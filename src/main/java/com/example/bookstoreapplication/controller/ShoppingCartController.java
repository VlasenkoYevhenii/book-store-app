package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.dto.cartitem.CartItemRequestDto;
import com.example.bookstoreapplication.dto.cartitem.CartItemUpdateDto;
import com.example.bookstoreapplication.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.bookstoreapplication.model.User;
import com.example.bookstoreapplication.service.shoppingcart.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    public ShoppingCartResponseDto getShoppingCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.getByUserId(user.getId());
    }

    @PostMapping
    public ShoppingCartResponseDto addBookToShoppingCart(Authentication authentication,
                                                 @RequestBody CartItemRequestDto cartItemRequestDto
                                                         ) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.addBookToCart(user.getId(), cartItemRequestDto);
    }

    @PutMapping("/cart-items/{cartItemId}")
    public ShoppingCartResponseDto updateBookQuantity(@PathVariable Long cartItemId,
                                                      Authentication authentication,
                                                      CartItemUpdateDto updateDto) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.updateBookQuantityById(cartItemId, user.getId(), updateDto);
    }

    @DeleteMapping("/cart-items/{cartItemId}")
    public void removeBookFromCart(@PathVariable Long cartItemId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        shoppingCartService.removeBookFromCart(user.getId(), cartItemId);
    }
}
