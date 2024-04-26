package com.example.bookstoreapplication.repository.book;

import com.example.bookstoreapplication.dto.BookSearchParameters;
import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.repository.SpecificationBuilder;
import com.example.bookstoreapplication.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private static final String TITLE_KEY = "title";
    private static final String AUTHOR_KEY = "title";
    private final SpecificationProviderManager<Book> providerManager;

    @Override
    public Specification<Book> build(BookSearchParameters parameters) {
        Specification<Book> spec = Specification.where(null);
        if (ArrayUtils.isNotEmpty(parameters.titles())) {
            spec = spec.and(providerManager.getSpecificationProvider(TITLE_KEY)
                    .getSpecification(parameters.titles()));
        }
        if (ArrayUtils.isNotEmpty(parameters.authors())) {
            spec = spec.and(providerManager.getSpecificationProvider(AUTHOR_KEY)
                    .getSpecification(parameters.authors()));
        }
        return spec;
    }
}
