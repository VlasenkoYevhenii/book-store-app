package com.example.bookstoreapplication.dto.shoppingcart;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ShoppingCartRequestDto {
    @Positive
    private Integer quantity;
}
