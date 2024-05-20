package com.example.bookstoreapplication.dto.order;

import com.example.bookstoreapplication.dto.orderitem.OrderItemResponseDto;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private Set<OrderItemResponseDto> orderItems;
    private String orderDate;
    private BigDecimal total;
    private String status;
}
