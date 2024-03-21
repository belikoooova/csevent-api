package com.example.cseventapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OrganizationWithSuchNicknameAlreadyExists extends CustomException {
    private final String defaultMessage = "Организация с таким никнеймом уже существует";
    private final HttpStatus status = HttpStatus.CONFLICT;
}
