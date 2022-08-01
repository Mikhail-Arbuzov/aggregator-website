package com.aggregator.aggregator_website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailForRecoveryPasswordDto {
    @NotBlank(message = "Поле для ввода почты незаполнено!")
    @Email(message = "Не верно указана почта!",regexp = "^([A-Za-z0-9]{1,}[\\\\.-]{0,1}[A-Za-z0-9]{1,})+@([A-Za-zА-Яа-я0-9]{1,}[\\\\.-]{0,1}[A-Za-zА-Яа-я0-9]{1,})+[\\\\.]{1}[a-zа-я]{2,4}+$")
    private String email;
}
