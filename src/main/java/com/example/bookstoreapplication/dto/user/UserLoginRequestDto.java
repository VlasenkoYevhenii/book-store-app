package com.example.bookstoreapplication.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserLoginRequestDto {
    @NotBlank(message = "The email field is empty")
    @Email(message = "The email format is invalid")
    private String email;

    @NotBlank(message = "The password field is empty")
    @Length(min = 6)
    private String password;
}
