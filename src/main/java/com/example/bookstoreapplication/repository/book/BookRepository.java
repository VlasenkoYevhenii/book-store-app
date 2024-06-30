package com.example.bookstoreapplication.repository.book;

import com.example.bookstoreapplication.model.Book;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    @EntityGraph(attributePaths = "categories")
    Page<Book> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "categories")
    Page<Book> findAll(Specification<Book> specification, Pageable pageable);

    @EntityGraph(attributePaths = "categories")
    Optional<Book> findById(Long id);

    @Query("SELECT b FROM Book b JOIN FETCH b.categories c")
    List<Book> findAllWithCategories(Specification<Book> spec);

    @Query(value = "SELECT * FROM books "
            + "LEFT JOIN books_categories ON books.id = books_categories.book_id "
            + "WHERE books_categories.category_id = :id",
            nativeQuery = true)
    Page<Book> findByCategoriesId(Long id, Pageable pageable);
}
