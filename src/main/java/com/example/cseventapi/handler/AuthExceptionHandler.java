package com.example.cseventapi.handler;

import com.example.cseventapi.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {
    private static final String LOGIN_INCORRECT_DATA = "Неверный логин и/или пароль";

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(e.getDefaultMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ignored) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(LOGIN_INCORRECT_DATA);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getFieldError() == null || ex.getFieldError().getDefaultMessage() == null
                        ? ""
                        : ex.getFieldError().getDefaultMessage());
    }
}
