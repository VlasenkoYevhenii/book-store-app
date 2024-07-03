package com.example.bookstoreapplication.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.ISBN;

@Accessors(chain = true)
@Data
public class CreateBookRequestDto {
    @NotBlank(message = "Define the book title")
    private String title;

    @NotBlank(message = "Define the book author")
    private String author;

    @NotBlank(message = "Define the book isbn")
    @ISBN
    private String isbn;

    @NotNull(message = "Define the book price")
    @Positive(message = "The price must be greater than 0")
    private BigDecimal price;

    private String description;
    private String coverImage;
    @NotEmpty
    private Set<Long> categoryIds;
}
