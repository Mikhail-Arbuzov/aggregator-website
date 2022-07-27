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
public class DescriptionUserRequest {
    @NotBlank(message = "Поле для ввода описания о себе незаполнено!")
    @Pattern(regexp = "^([^\\s].+[^@#$%^&()+=/*/\\/\\]\\[{}><|])?$",message = "Возможно был поставлен лишний пробел в начале или в конце строки! Недопустимо указывание данных символов:~ @ # $ % ^ & * + = () <> [] {} \\ / |")
    private String description;
}
