package com.example.cseventapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInOrganizationRequest {
    private static final String NICKNAME_SIZE = "Никнейм организации должен содержать от 5 до 25 символов";
    private static final String NICKNAME_REGEX = "^[A-Za-z0-9._]{5,25}$";
    private static final String NICKNAME_INVALID = "Никнейм организации может состоять только из символов: A-Za-z0-9._";
    private static final String SECRET_CODE_REGEX = "\\d{6}";
    private static final String SECRET_CODE_INVALID = "Секретный код организации должен состоять из 6 цифр";

    @Size(min = 5, max = 25, message = NICKNAME_SIZE)
    @Pattern(regexp = NICKNAME_REGEX, message = NICKNAME_INVALID)
    private String nickname;

    @JsonProperty("secret_code")
    @Pattern(regexp = SECRET_CODE_REGEX, message = SECRET_CODE_INVALID)
    private String secretCode;
}
