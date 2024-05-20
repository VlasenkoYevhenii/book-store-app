package com.example.bookstoreapplication.dto.cartitem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartItemUpdateDto(
        @NotNull(message = "Quantity must not be null")
        @Min(value = 1, message = "Quantity must be greater than zero")
        Integer quantity) {
}
