package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.dto.cartitem.CartItemRequestDto;
import com.example.bookstoreapplication.dto.cartitem.CartItemUpdateDto;
import com.example.bookstoreapplication.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.bookstoreapplication.model.User;
import com.example.bookstoreapplication.service.shoppingcart.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
@Tag(name = "Shopping Cart", description = "Shopping Cart management")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    @Operation(summary = "Get the shoppingCart",
            description = "Endpoint for getting user's shoppingCart from DB")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingCartResponseDto getShoppingCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.getByUserId(user.getId());
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    @Operation(summary = "Add a cartItem to the shopping cart",
            description = "Endpoint for adding a cartItem to the shopping cart")
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCartResponseDto addBookToShoppingCart(Authentication authentication,
                                         @RequestBody @Valid CartItemRequestDto cartItemRequestDto
    ) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.addBookToCart(user.getId(), cartItemRequestDto);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/cart-items/{cartItemId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update books quantity",
            description = "Endpoint for updating books quantity in the shopping cart")
    public ShoppingCartResponseDto updateBookQuantity(@PathVariable Long cartItemId,
                                                      Authentication authentication,
                                              @RequestBody @Valid CartItemUpdateDto updateDto) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.updateBookQuantityById(cartItemId, user.getId(), updateDto);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/cart-items/{cartItemId}")
    @Operation(summary = "Delete a cartItem",
            description = "Endpoint for deleting a cart item DB")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBookFromCart(@PathVariable Long cartItemId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        shoppingCartService.removeBookFromCart(user.getId(), cartItemId);
    }
}
