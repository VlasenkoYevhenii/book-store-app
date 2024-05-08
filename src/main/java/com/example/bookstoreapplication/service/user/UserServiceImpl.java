package com.example.bookstoreapplication.service.user;

import com.example.bookstoreapplication.dto.user.UserRegistrationRequestDto;
import com.example.bookstoreapplication.dto.user.UserResponseDto;
import com.example.bookstoreapplication.exception.RegistrationException;
import com.example.bookstoreapplication.mapper.UserMapper;
import com.example.bookstoreapplication.model.User;
import com.example.bookstoreapplication.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto dto) throws RegistrationException {
        if (isRegistered(dto.getEmail())) {
            throw new RegistrationException("User already exists");
        }
        return mapper.toDto(repository.save(mapper.toModel(dto)));
    }

    private boolean isRegistered(String email) {
        User user;
        user = repository.findByEmail(email).orElse(null);
        return user != null;
    }
}
