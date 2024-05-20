package com.example.bookstoreapplication.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestStatusUpdateDto {
    @NotBlank
    private String status;
}
