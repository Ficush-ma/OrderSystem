package com.orderSystem.controller;


import com.orderSystem.DTO.OrderDTO;
import com.orderSystem.entity.Result;
import com.orderSystem.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }
    @PostMapping("/takeOrders")
    @Operation(summary = "创建订单")
    public void createOrder(@RequestBody OrderDTO orderDTO){
        orderService.createOrder(orderDTO);
        Result.success();
    }
}
