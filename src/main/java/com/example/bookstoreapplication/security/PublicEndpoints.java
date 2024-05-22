package com.example.bookstoreapplication.security;

import java.util.List;
import lombok.Getter;

public class PublicEndpoints {
    @Getter
    private static List<String> endpoints =
            List.of("/api/auth/login", "/api/auth/registration");

}
