package com.orderSystem.common;

import com.orderSystem.entity.OrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "order", consumerGroup = "order-consumer")
@Slf4j
public class OrderConsumer implements RocketMQListener<OrderMessage> {
    @Override
    public void onMessage(OrderMessage orderMessage) {
        log.info("有新的订单，产品号: {}，用户: {}",orderMessage.getProductId(), orderMessage.getUserId());
    }
}
