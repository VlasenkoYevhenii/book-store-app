package com.example.bookstoreapplication.repository.book.spec;

import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Book> {
    public Specification<Book> getSpecification(String[] params) {
        return ((root, query, criteriaBuilder) -> root.get("author").in(Arrays.stream(params).toArray()));
    }

    @Override
    public String getKey() {
        return "author";
    }
}
