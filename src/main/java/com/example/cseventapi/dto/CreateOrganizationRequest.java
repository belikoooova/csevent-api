package com.example.cseventapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrganizationRequest {
    private static final String TITLE_NOT_BLANK = "Название организации не может быть пустым";
    private static final String TITLE_NOT_NULL= "Название организации не распознано";
    private static final String NICKNAME_REGEX = "^[A-Za-z0-9._]{5,25}$";
    private static final String NICKNAME_INVALID = "Никнейм организации может состоять только из символов: "
            + "a-z0-9._ и быть длиной от 5 до 25";
    private static final String SECRET_CODE_REGEX = "\\d{6}";
    private static final String SECRET_CODE_INVALID = "Секретный код организации должен состоять из 6 цифр";

    @NotBlank(message = TITLE_NOT_BLANK)
    @NotNull(message = TITLE_NOT_NULL)
    private String title;

    @Pattern(regexp = NICKNAME_REGEX, message = NICKNAME_INVALID)
    private String nickname;

    @JsonProperty("secret_code")
    @Pattern(regexp = SECRET_CODE_REGEX, message = SECRET_CODE_INVALID)
    private String secretCode;
}
