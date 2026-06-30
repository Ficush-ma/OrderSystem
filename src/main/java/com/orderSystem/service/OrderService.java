package com.orderSystem.service;

import com.orderSystem.DTO.OrderDTO;
import com.orderSystem.common.BaseContext;
import com.orderSystem.entity.Order;
import com.orderSystem.entity.OrderMessage;
import com.orderSystem.entity.Product;
import com.orderSystem.entity.User;
import com.orderSystem.exception.AmountException;
import com.orderSystem.exception.BaseException;
import com.orderSystem.exception.StockException;
import com.orderSystem.mapper.OrderMapper;
import com.orderSystem.mapper.ProductMapper;
import com.orderSystem.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OrderService {
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;
    private final ProductMapper productMapper;
    private final RedissonClient redissonClient;
    private final RocketMQTemplate rocketMQTemplate;
    public OrderService(
            OrderMapper orderMapper, UserMapper userMapper, ProductMapper productMapper,
            RedissonClient redissonClient, RocketMQTemplate rocketMQTemplate){
        this.userMapper = userMapper;
        this.orderMapper = orderMapper;
        this.productMapper = productMapper;
        this.redissonClient = redissonClient;
        this.rocketMQTemplate = rocketMQTemplate;
    }
    @Transactional(rollbackForClassName = {"AmountException", "StockException","BaseException"})
    public void createOrder(OrderDTO orderDTO){
        User user = userMapper.getUserById(BaseContext.getCurrentUser());
        Product product = productMapper.searchProduct(orderDTO.getProductId());
        if (user.getBalance() < orderDTO.getAmount()){
            throw new AmountException(1001,"用户余额不足");
        } else if (product.getStock() < orderDTO.getQuantity()) {
            throw new StockException(2001,"库存不足");
        } else {
            RLock userLock = redissonClient.getLock("lock:user:"+user.getId());
            RLock ProductLock = redissonClient.getLock("lock:product:"+product.getId());
            //redission加锁（修改用户余额和产品库存）
            try{
                boolean getLock = userLock.tryLock(5, 10, TimeUnit.SECONDS);
                if (!getLock){
                    throw new BaseException(1002,"服务繁忙，请稍后再试");
                }
                userMapper.setBalance(user.getId(), user.getBalance()-orderDTO.getAmount());
            } catch (Exception e) {
                throw new AmountException(1003,"修改用户余额失败");
            }finally {
                if (userLock.isHeldByCurrentThread()){
                    userLock.unlock();
                }
            }
            try{
                boolean getLock = ProductLock.tryLock(5, 10, TimeUnit.SECONDS);
                if (!getLock){
                    throw new BaseException(2002,"服务繁忙，请稍后再试");
                }
                productMapper.setStock(product.getId(), product.getStock()-orderDTO.getQuantity());
            } catch (Exception e) {
                throw new AmountException(2003,"修改产品库存失败");
            }finally {
                if (ProductLock.isHeldByCurrentThread()){
                    ProductLock.unlock();
                }
            }

            /*
                测试事务是否成功实现（此处抛出异常若无事务回滚，会减少库存但无创建订单）
                if(true){throw new AmountException(801,"事务测试");}
             */
            orderMapper.createOrder(orderDTO, BaseContext.getCurrentUser());
            log.info("用户 {} 创建订单,产品数量: {}",user.getUserName(),orderDTO.getQuantity());
            //RocketMQ发送异步消息完成通知
            OrderMessage new_message = new OrderMessage(user.getId(), product.getId());
            rocketMQTemplate.syncSend("order",new_message);
            log.info("RocketMQ消息发送");
        }
    }
}
