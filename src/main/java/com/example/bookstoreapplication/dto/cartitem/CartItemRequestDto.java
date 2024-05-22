package com.example.bookstoreapplication.dto.cartitem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemRequestDto {
    @NotNull
    @Min(1)
    private Long bookId;
    @NotNull
    @Min(1)
    private Integer quantity;
}
