package com.example.cseventapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OrganizationWrongSecretCode extends CustomException {
    private final String defaultMessage = "Неверный секретный код";
    private final HttpStatus status = HttpStatus.UNAUTHORIZED;
}
