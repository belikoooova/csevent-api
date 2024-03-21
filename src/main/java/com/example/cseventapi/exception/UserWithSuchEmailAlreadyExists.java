package com.example.cseventapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserWithSuchEmailAlreadyExists extends CustomException {
    private final String defaultMessage = "Пользователь с такой почтой уже существует";
    private final HttpStatus status = HttpStatus.CONFLICT;
}
