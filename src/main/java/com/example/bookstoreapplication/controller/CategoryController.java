package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.dto.book.BookDtoWithoutCategoryIds;
import com.example.bookstoreapplication.dto.category.CategoryDto;
import com.example.bookstoreapplication.dto.category.CategoryRequestDto;
import com.example.bookstoreapplication.service.book.BookService;
import com.example.bookstoreapplication.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
@Tag(name = "Categories", description = "Category management")
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Category creation",
                description = "Add new category to the DB(ADMIN only)")
    public CategoryDto createCategory(CategoryRequestDto dto) {
        return categoryService.save(dto);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get all categories",
                description = "Retrieves all the categories from the DB")
    public List<CategoryDto> getAll(Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get category by id",
                description = "Retrieves a category by id from the DB")
    public CategoryDto getCategoryById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update category",
                description = "Updates category by its id(ADMIN only)")
    public CategoryDto updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDto dto) {
        return categoryService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete category",
                description = "Deletes category by its id from the DB (ADMIN only)")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
    }

    @GetMapping("/{id}/books")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get books by category id",
                description = "Retrieves all books by category id")
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(@PathVariable Long id,
                                                                Pageable pageable) {
        return bookService.getBookDtosByCategoryId(id, pageable);
    }
}
