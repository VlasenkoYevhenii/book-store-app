package com.example.bookstoreapplication.dto.shoppingcart;

import com.example.bookstoreapplication.model.CartItem;
import lombok.Data;

import java.util.Set;

@Data
public class ShoppingCartResponseDto {
    private Long id;
    private Long userId;
    private Set<CartItem> cartItems;
}
