package com.example.bookstoreapplication.repository.category;

import com.example.bookstoreapplication.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
