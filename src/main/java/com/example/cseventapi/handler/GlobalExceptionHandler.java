package com.example.cseventapi.handler;

import com.example.cseventapi.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String INCORRECT_REQUEST = "Некорректный запрос";
    private static final String NOT_FOUND = "Извините, запрашиваемый ресурс не был найден";

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ignored) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(INCORRECT_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ignored) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(NOT_FOUND);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(e.getDefaultMessage());
    }
}
