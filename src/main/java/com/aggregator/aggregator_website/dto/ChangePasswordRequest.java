package com.aggregator.aggregator_website.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {
    @NotBlank(message = "Не указан текущий пароль!")
    private String currentPass;
    @NotBlank(message = "Не указан новый пароль!")
    @Size(min = 5, message = "Новый пароль должен содержать минимум 5 символов!")
    private  String newPass;
    @NotBlank(message = "Не указан подтверждающий пароль!")
    private String confirmPass;
}
