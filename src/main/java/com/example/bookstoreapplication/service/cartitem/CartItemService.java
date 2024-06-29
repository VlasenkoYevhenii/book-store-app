package com.example.bookstoreapplication.service.cartitem;

import com.example.bookstoreapplication.dto.cartitem.CartItemRequestDto;
import com.example.bookstoreapplication.dto.cartitem.CartItemResponseDto;
import com.example.bookstoreapplication.model.CartItem;
import com.example.bookstoreapplication.model.ShoppingCart;

public interface CartItemService {
    CartItemResponseDto findById(Long cartItemId);

    void deleteById(Long cartItemId);

    void deleteCartItem(CartItem cartItem);

    CartItemResponseDto createCartItem(ShoppingCart shoppingCart, CartItemRequestDto requestDto);

    CartItemResponseDto save(CartItem cartItem);
}
