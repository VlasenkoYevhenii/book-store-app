package com.example.bookstoreapplication.dto.shoppingcart;

import com.example.bookstoreapplication.model.CartItem;
import java.util.Set;
import lombok.Data;

@Data
public class ShoppingCartResponseDto {
    private Long id;
    private Long userId;
    private Set<CartItem> cartItems;
}
