package com.example.bookstoreapplication.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryDto {
    @NotBlank(message = "Add category name")
    private String name;
    private String description;
}
