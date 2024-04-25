package com.example.bookstoreapplication.repository.book;

import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.repository.SpecificationProvider;
import com.example.bookstoreapplication.repository.SpecificationProviderManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> providers;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return providers.stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst().orElseThrow(() -> new RuntimeException(
                        "Failed to find correct specification provider for key " + key));
    }
}
