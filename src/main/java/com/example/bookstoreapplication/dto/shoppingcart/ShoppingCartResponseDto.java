package com.example.bookstoreapplication.dto.shoppingcart;

import com.example.bookstoreapplication.dto.cartitem.CartItemResponseDto;
import java.util.Set;
import lombok.Data;

@Data
public class ShoppingCartResponseDto {
    private Long id;
    private Long userId;
    private Set<CartItemResponseDto> cartItems;
}
