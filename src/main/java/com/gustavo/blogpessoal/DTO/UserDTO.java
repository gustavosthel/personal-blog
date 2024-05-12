package com.gustavo.blogpessoal.DTO;

import com.gustavo.blogpessoal.entity.user.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UserDTO(@NotBlank @Size(min = 3, max = 20) String username,
                      @NotBlank @Email String email,
                      @NotBlank @Size(min = 6) String password,
                      UserType type) {
}
