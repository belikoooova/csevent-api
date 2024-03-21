package com.example.cseventapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OrganizationWithSuchNicknameNotExist extends CustomException {
    private final String defaultMessage = "Организации с таким никнеймом не существует";
    private final HttpStatus status = HttpStatus.NOT_FOUND;
}
