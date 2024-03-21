package com.example.cseventapi.handler;

import com.example.cseventapi.exception.UserWithSuchEmailAlreadyExists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {
    private static final String REGISTER_USER_ALREADY_EXISTS = "User with such email already exists";
    private static final String LOGIN_EMAIL_NOT_FOUND = "User with such email not exists";
    private static final String LOGIN_INCORRECT_DATA = "Incorrect email or password";

    @ExceptionHandler(UserWithSuchEmailAlreadyExists.class)
    public ResponseEntity<String> handleUserWithSuchEmailAlreadyExists(UserWithSuchEmailAlreadyExists ignored) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(REGISTER_USER_ALREADY_EXISTS);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ignored) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(LOGIN_INCORRECT_DATA);
    }
}
