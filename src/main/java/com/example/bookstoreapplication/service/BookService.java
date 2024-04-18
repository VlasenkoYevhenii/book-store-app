package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.model.Book;
import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
