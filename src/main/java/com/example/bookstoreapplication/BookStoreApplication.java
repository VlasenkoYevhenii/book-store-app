package com.example.bookstoreapplication;

import com.example.bookstoreapplication.mapper.BookMapper;
import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
@RequiredArgsConstructor
public class BookStoreApplication {
    private final BookService bookService;
    private final BookMapper mapper;

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }
}
