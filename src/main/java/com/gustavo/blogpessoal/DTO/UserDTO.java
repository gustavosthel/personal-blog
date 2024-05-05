package com.gustavo.blogpessoal.DTO;

import com.gustavo.blogpessoal.entity.user.UserType;

import java.util.UUID;

public record UserDTO(String username, String email, String password, UserType type) {
}
