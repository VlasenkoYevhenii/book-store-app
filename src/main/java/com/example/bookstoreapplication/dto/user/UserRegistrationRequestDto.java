package com.example.bookstoreapplication.dto.user;

import com.example.bookstoreapplication.fieldvalidator.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch(first = "password", second = "repeatPassword",
        message = "Repeated password must match the password")
public class UserRegistrationRequestDto {
    @NotBlank(message = "Please add your email")
    @Email(message = "Incorrect email pattern")
    private String email;

    @NotBlank
    @Length(min = 6, message = "The password must be at least 6 characters long")
    private String password;

    private String repeatPassword;

    @NotBlank(message = "Please add your first name")
    private String firstName;

    @NotBlank(message = "Please add your last name")
    private String lastName;
    private String shippingAddress;
}
