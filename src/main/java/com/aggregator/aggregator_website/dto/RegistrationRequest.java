package com.aggregator.aggregator_website.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationRequest {
    @NotBlank(message = "Не указан логин!")
    @Pattern(regexp = "^[a-zA-Z0-9]+$",message = "Возможно был поставлен лишний пробел в начале или в конце строки!Нужно указывать только цыфры и латинские символы!")
    private String login;

    @NotBlank(message = "Не указана почта!")
    @Email(message = "Не верно указана почта!")
    private String email;

    @NotBlank(message = "Не указан пароль!")
    @Size(min = 5, message = "Пароль должен содержать минимум 5 символов!")
    private String password;

    @NotBlank(message = "Не указан подтверждающий пароль!")
    @Size(min = 5, message = "Подтверждающий пароль должен содержать минимум 5 символов!")
    private String confirmPassword;
}
