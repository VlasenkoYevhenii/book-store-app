package com.example.bookstoreapplication.mapper;

import com.example.bookstoreapplication.config.MapperConfig;
import com.example.bookstoreapplication.dto.user.CreateUserRequestDto;
import com.example.bookstoreapplication.dto.user.ResponseUserDto;
import com.example.bookstoreapplication.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toModel(CreateUserRequestDto dto);

    ResponseUserDto toDto(User user);
}
