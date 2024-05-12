package com.gustavo.blogpessoal.DTO;

import com.gustavo.blogpessoal.entity.user.UserType;

public record ResponseDTO(UserType type, String token) {
}
