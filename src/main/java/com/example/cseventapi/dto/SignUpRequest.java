package com.example.cseventapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private static final String NAME_NOT_BLANK = "Имя пользователя не может быть пустым";
    private static final String EMAIL_NOT_BLANK = "Email адрес не может быть пустым";
    private static final String EMAIL_INVALID = "Email адрес должен быть в формате user@example.com";
    private static final String PASSWORD_NOT_BLANK = "Email адрес не может быть пустым";

    @Email(message = EMAIL_INVALID)
    @NotBlank(message = EMAIL_NOT_BLANK)
    private String email;

    @NotBlank(message = NAME_NOT_BLANK)
    private String name;

    @NotBlank(message = PASSWORD_NOT_BLANK)
    private String password;
}
