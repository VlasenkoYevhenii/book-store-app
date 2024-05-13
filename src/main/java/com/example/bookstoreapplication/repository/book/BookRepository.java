package com.example.bookstoreapplication.repository.book;

import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    List<Book> findByCategoriesId(Long id);
}
