package com.orderSystem.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private int productId;
    private int quantity;
    private int amount;

}
