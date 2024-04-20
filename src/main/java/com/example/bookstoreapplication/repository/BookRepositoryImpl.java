package com.example.bookstoreapplication.repository;

import com.example.bookstoreapplication.dto.BookDto;
import com.example.bookstoreapplication.mapper.BookMapper;
import com.example.bookstoreapplication.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    private final EntityManagerFactory factory;

    @Override
    public Book save(Book book) {
        EntityManager manager;
        EntityTransaction transaction = null;
        try {
            manager = factory.createEntityManager();
            transaction = manager.getTransaction();
            transaction.begin();
            manager.persist(book);
            transaction.commit();
            return book;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to save book " + book, e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (EntityManager entityManager = factory.createEntityManager()) {
            return entityManager.createQuery("from Book", Book.class).getResultList();
        }
    }
}
