package com.example.bookstoreapplication.service.shoppingcart;

import com.example.bookstoreapplication.dto.cartitem.CartItemRequestDto;
import com.example.bookstoreapplication.dto.cartitem.CartItemUpdateDto;
import com.example.bookstoreapplication.dto.shoppingcart.ShoppingCartResponseDto;

public interface ShoppingCartService {
    ShoppingCartResponseDto getByUserId(Long userId);

    ShoppingCartResponseDto addBookToCart(Long userId, CartItemRequestDto requestDto);

    ShoppingCartResponseDto updateBookQuantityById(Long cartItemId, Long userId,
                                                   CartItemUpdateDto updateDto);

    void removeBookFromCart(Long userId, Long cartItemId);
}
