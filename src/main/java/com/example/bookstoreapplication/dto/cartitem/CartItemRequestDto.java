package com.example.bookstoreapplication.dto.cartitem;

import lombok.Data;

@Data
public class CartItemRequestDto {
    private Long bookId;
    private int quantity;
}