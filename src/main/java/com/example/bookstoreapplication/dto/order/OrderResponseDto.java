package com.example.bookstoreapplication.dto.order;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private List<OrderItemResponseDto> orderItems;
    private String orderDate;
    private BigDecimal total;
    private String status;
}
