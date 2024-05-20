package com.example.bookstoreapplication.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestShippingAddressDto {
    @NotBlank
    @Size(min = 10, max = 100)
    private String shippingAddress;
}
