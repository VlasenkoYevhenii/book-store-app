package com.example.bookstoreapplication.dto.shoppingcart;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ShoppingCartRequestDto {
    @Positive
    private int quantity;
}
