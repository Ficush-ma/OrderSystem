一个简单的OrderSystem
mysql版本：8.4.7
redis版本: 3.0.504
RocketMQ版本: 5.1.4

Swagger地址: http://localhost:8082/swagger-ui/index.html

使用LoginInterceptor拦截进行JWT用户验证
redis登陆后存放jwt，多次登录时可以从缓存获取
OrderService中使用Redission锁，事务防止超卖、不一致发生。
RocketMQ进行异步通知，提醒新订单