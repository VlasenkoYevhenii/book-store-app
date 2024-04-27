package com.example.bookstoreapplication.repository.book.spec;

import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.repository.SpecificationProvider;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Book> {
    public static final String AUTHOR_KEY = "author";

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String searchString : params) {
                String lowercaseSearchString = searchString.toLowerCase();
                Predicate likePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(AUTHOR_KEY)),
                        "%" + lowercaseSearchString + "%"
                );
                predicates.add(likePredicate);
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public String getKey() {
        return AUTHOR_KEY;
    }
}
