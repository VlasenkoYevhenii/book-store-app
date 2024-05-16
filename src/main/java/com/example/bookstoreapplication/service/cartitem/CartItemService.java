package com.example.bookstoreapplication.service.cartitem;

import com.example.bookstoreapplication.dto.cartitem.CartItemRequestDto;
import com.example.bookstoreapplication.dto.cartitem.CartItemResponseDto;
import com.example.bookstoreapplication.dto.cartitem.CartItemUpdateDto;

public interface CartItemService {
    CartItemResponseDto save(CartItemRequestDto cartItemRequestDto);

    void updateCartItem(Long cartItemId, CartItemUpdateDto updateDto);
}
