package com.example.bookstoreapplication.repository.book.spec;

import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.repository.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Book> {
    private static final String AUTHOR_KEY = "author";

    public Specification<Book> getSpecification(String[] params) {
        return ((root, query, criteriaBuilder)
                    -> root.get(AUTHOR_KEY).in(Arrays.stream(params).toArray()));
    }

    @Override
    public String getKey() {
        return AUTHOR_KEY;
    }
}
