package com.example.bookstoreapplication.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequestDto {
    @NotBlank(message = "Category name can not be blank")
    @Size(min = 5, max = 40, message = "Category name must be from 5 to 40 characters long")
    private String name;
    @Size(max = 255, message = "Max description length is 255 characters")
    private String description;
}
