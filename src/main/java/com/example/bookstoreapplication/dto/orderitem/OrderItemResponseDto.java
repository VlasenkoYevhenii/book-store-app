package com.example.bookstoreapplication.dto.orderitem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponseDto {
    private Long id;
    private Long bookId;
    private int quantity;
}
