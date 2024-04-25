package com.example.bookstoreapplication.repository.book;

import com.example.bookstoreapplication.dto.BookSearchParameters;
import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.repository.SpecificationBuilder;
import com.example.bookstoreapplication.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> providerManager;
    @Override
    public Specification<Book> build(BookSearchParameters parameters) {
        Specification<Book> spec = Specification.where(null);
        if (checkTitles(parameters)) {
            spec = spec.and(providerManager.getSpecificationProvider("title")
                    .getSpecification(parameters.titles()));
        }
        if (checkAuthors(parameters)) {
            spec = spec.and(providerManager.getSpecificationProvider("author")
                    .getSpecification(parameters.authors()));
        }
        return spec;
    }

    private boolean checkTitles(BookSearchParameters parameters) {
        return parameters.titles() != null && parameters.titles().length > 0;
    }

    private boolean checkAuthors(BookSearchParameters parameters) {
        return parameters.authors() != null && parameters.authors().length > 0;
    }
}
