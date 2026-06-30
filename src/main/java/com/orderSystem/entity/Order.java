package com.orderSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private int id;
    private int userId;
    private int productId;
    private int quantity;
    private int amount;
    private int status;
    private LocalDateTime createdAt;
}
