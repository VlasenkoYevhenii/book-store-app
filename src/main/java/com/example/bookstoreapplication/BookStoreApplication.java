package com.example.bookstoreapplication;

import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class BookStoreApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            System.out.println("--RUN-- method was called");

            Book book = new Book();
            book.setTitle("title");
            book.setAuthor("author");
            book.setIsbn("isbn");
            book.setPrice(BigDecimal.valueOf(100));

            bookService.save(book);
            System.out.println(bookService.findAll());
            System.out.println(book);
        };
    }

}
