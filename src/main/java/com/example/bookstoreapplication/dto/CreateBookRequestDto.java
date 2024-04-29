package com.example.bookstoreapplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateBookRequestDto {
    @NotBlank(message = "Define the book title")
    private String title;

    @NotBlank(message = "Define the book author")
    private String author;

    @NotBlank(message = "Define the book isbn")
    @Pattern(regexp = "^\\d{13}$", message = "The ISBN must contain exactly 13 digits")
    private String isbn;

    @NotNull(message = "Define the book price")
    @Positive(message = "The price must be greater than 0")
    private BigDecimal price;

    private String description;
    private String coverImage;
}
