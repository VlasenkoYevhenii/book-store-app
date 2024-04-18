package com.example.bookstoreapplication.repository;

import com.example.bookstoreapplication.model.Book;
import java.util.List;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
