package com.example.bookstoreapplication.repository.book.spec;

import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {
    public static final String TITLE_KEY = "title";
    private static final int FIRST_ELEMENT = 0;

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> {
            String searchString = params[FIRST_ELEMENT];
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(TITLE_KEY)),
                    "%" + searchString.toLowerCase() + "%"
            );
        };
    }

    @Override
    public String getKey() {
        return TITLE_KEY;
    }
}
