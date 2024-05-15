package com.gustavo.blogpessoal.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostDTO(@NotBlank @Size(min = 3, max = 250) String content) {
}
