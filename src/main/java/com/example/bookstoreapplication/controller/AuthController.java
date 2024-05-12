package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.dto.user.UserRegistrationRequestDto;
import com.example.bookstoreapplication.dto.user.UserResponseDto;
import com.example.bookstoreapplication.exception.RegistrationException;
import com.example.bookstoreapplication.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.awt.desktop.UserSessionListener;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Users", description = "Users authentication and login operations")
public class AuthController {
    private final UserService service;

    @PostMapping("login")
    public UserLoginResponseDto login(UserLoginRequestDto dto) {
        return null;
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register new user",
                description = "Registers a user if it's not registered yet")
    public UserResponseDto register(@Valid @RequestBody UserRegistrationRequestDto dto)
                throws RegistrationException {
        return service.register(dto);
    }
}
