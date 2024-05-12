package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.dto.user.UserLoginRequestDto;
import com.example.bookstoreapplication.dto.user.UserLoginResponseDto;
import com.example.bookstoreapplication.dto.user.UserRegistrationRequestDto;
import com.example.bookstoreapplication.dto.user.UserResponseDto;
import com.example.bookstoreapplication.exception.RegistrationException;
import com.example.bookstoreapplication.security.AuthenticationService;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Users", description = "Users authentication and login operations")
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Login endpoint",
                description = "Returns JWT token in response")
    public UserLoginResponseDto login(UserLoginRequestDto dto) {
        return authenticationService.authenticate(dto);
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register new user",
                description = "Registers a user if it's not registered yet")
    public UserResponseDto register(@Valid @RequestBody UserRegistrationRequestDto dto)
                throws RegistrationException {
        return userService.register(dto);
    }
}
