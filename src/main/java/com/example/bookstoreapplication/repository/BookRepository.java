package com.example.bookstoreapplication.repository;

import com.example.bookstoreapplication.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
