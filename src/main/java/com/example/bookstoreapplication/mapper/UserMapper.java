package com.example.bookstoreapplication.mapper;

import com.example.bookstoreapplication.config.MapperConfig;
import com.example.bookstoreapplication.dto.user.UserRegistrationRequestDto;
import com.example.bookstoreapplication.dto.user.UserResponseDto;
import com.example.bookstoreapplication.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toModel(UserRegistrationRequestDto dto);

    UserResponseDto toDto(User user);
}
