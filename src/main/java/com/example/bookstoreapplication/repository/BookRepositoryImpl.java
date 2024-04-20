package com.example.bookstoreapplication.repository;

import com.example.bookstoreapplication.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
            return entityManager.createQuery("FROM Book", Book.class).getResultList();
        }
    }

    @Override
    public Book getById(Long id) {
        try (EntityManager entityManager = factory.createEntityManager()) {
            TypedQuery<Book> query = entityManager.createQuery("FROM Book b "
                        + "WHERE b.id =:id", Book.class);
            query.setParameter("id",id);
            return query.getSingleResult();
        }
    }
}
