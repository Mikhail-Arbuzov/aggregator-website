package com.aggregator.aggregator_website.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class DomainRequest {
    @NotBlank(message = "Домен сайта не был указан!")
//    @Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9-]{1,61}[a-zA-Z0-9](?:\\.[a-zA-Z]{2,})+$",
//            message = "Неверно указан домен сайта!Возможно был поставлен лишний пробел в начале или в конце строки.")
    @Pattern(regexp = "^(?!\\-)(?:[a-zA-Z\\d\\-]{0,62}[a-zA-Z\\d]\\.){1,126}(?!\\d+)[a-zA-Z\\d]{1,63}$",
    message = "Неверно указан домен сайта!Возможно был поставлен лишний пробел в начале или в конце строки.")
    private String value;
}
