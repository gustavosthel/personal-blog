package com.gustavo.blogpessoal.DTO;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public record ExceptionDTO(String message, String statusCode) {
}
