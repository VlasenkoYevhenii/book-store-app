package com.example.bookstoreapplication.dto.user;

import lombok.Data;

@Data
public class ResponseUserDto {
      private Long id;
      private String email;
      private String firstName;
      private String lastName;
      private String shippingAddress;
}
