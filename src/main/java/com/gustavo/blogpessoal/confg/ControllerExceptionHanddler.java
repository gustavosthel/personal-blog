package com.gustavo.blogpessoal.confg;

import com.gustavo.blogpessoal.DTO.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHanddler extends ExceptionCustom {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity incorrectCredentials(BadCredentialsException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO("Email or Password incorrect", "401");
        return new ResponseEntity(exceptionDTO, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity handleEmailAlreadyExists(EmailAlreadyExistsException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO("Email already exists", "400");
        return new ResponseEntity(exceptionDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AdminAlreadyExistsException.class)
    public ResponseEntity handleAdminAlreadyExists(AdminAlreadyExistsException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO("Admin already exists", "400");
        return new ResponseEntity(exceptionDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IdUserAlreadyExistsException.class)
    public ResponseEntity handleIdUserAlreadyExists(IdUserAlreadyExistsException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO("User id not found", "404");
        return new ResponseEntity(exceptionDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleExceptionGeneral(Exception exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO("Internal Server Error", "500");
        return new ResponseEntity(exceptionDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
