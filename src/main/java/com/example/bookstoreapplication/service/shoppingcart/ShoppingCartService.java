package com.example.bookstoreapplication.service.shoppingcart;

import com.example.bookstoreapplication.dto.cartitem.CartItemRequestDto;
import com.example.bookstoreapplication.dto.shoppingcart.ShoppingCartRequestDto;
import com.example.bookstoreapplication.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.bookstoreapplication.model.ShoppingCart;
import com.example.bookstoreapplication.model.User;

public interface ShoppingCartService {
    ShoppingCartResponseDto createShoppingCart(User user);

    ShoppingCartResponseDto getShoppingCartDto(Long userId);

    ShoppingCartResponseDto clearShoppingCart(ShoppingCart shoppingCart);

    ShoppingCartResponseDto updateQuantityById(User user,
                                               Long cartItemId,
                                               ShoppingCartRequestDto requestDto);

    ShoppingCartResponseDto addBookToShoppingCart(User user,
                                                  CartItemRequestDto cartItemRequestDto);
}
