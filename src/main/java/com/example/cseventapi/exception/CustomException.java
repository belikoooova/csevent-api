package com.example.cseventapi.exception;

import org.springframework.http.HttpStatus;

public abstract class CustomException extends RuntimeException {
    public abstract String getDefaultMessage();
    public abstract HttpStatus getStatus();
}
