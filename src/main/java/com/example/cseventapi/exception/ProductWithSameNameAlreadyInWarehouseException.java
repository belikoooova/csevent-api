package com.example.cseventapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProductWithSameNameAlreadyInWarehouseException extends CustomException {
    private final String defaultMessage = "Продукт с таким названием уже существует на складе";
    private final HttpStatus status = HttpStatus.CONFLICT;
}
