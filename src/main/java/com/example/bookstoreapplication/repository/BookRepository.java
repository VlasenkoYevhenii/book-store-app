package com.example.bookstoreapplication.repository;

import com.example.bookstoreapplication.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

}
