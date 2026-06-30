package com.orderSystem.mapper;

import com.orderSystem.DTO.OrderDTO;
import com.orderSystem.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    void createOrder(OrderDTO orderDTO, int userId);


}
