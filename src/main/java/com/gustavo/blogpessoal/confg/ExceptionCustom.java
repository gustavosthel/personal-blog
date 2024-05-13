package com.gustavo.blogpessoal.confg;

public class ExceptionCustom {

    public static class EmailAlreadyExistsException extends RuntimeException {
        public EmailAlreadyExistsException() {
        }
    }

    public static class AdminAlreadyExistsException extends RuntimeException {
        public AdminAlreadyExistsException() {
        }
    }

    public static class IdUserAlreadyExistsException extends RuntimeException {
        public IdUserAlreadyExistsException() {
        }
    }


}
