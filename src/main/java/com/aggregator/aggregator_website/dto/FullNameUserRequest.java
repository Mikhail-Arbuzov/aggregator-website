package com.aggregator.aggregator_website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FullNameUserRequest {
    @NotBlank(message = "Поле для ввода имени незаполнено!")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ]+$",message = "Возможно был поставлен лишний пробел в начале или в конце строки!Нужно указывать только латинские или кириллические символы!")
    private String firstName;
    @NotBlank(message = "Поле для ввода фамилии незаполнено!")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ]+$",message = "Возможно был поставлен лишний пробел в начале или в конце строки!Нужно указывать только латинские или кириллические символы!")
    private String secondName;
}
