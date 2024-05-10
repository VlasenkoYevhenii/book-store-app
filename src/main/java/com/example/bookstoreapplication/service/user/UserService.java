package com.example.bookstoreapplication.service.user;

import com.example.bookstoreapplication.dto.user.UserRegistrationRequestDto;
import com.example.bookstoreapplication.dto.user.UserResponseDto;
import com.example.bookstoreapplication.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto dto) throws RegistrationException;
}
