package com.example.cseventapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProductWithSameNameAlreadyInCocktailException extends CustomException {
    private final String defaultMessage = "Продукт с таким названием уже существует в коктейле";
    private final HttpStatus status = HttpStatus.CONFLICT;
}
