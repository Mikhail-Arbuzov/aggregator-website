package com.aggregator.aggregator_website.dto;

import com.aggregator.aggregator_website.services.annotations.UrlExists;
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
public class SourceRequest {
    @NotBlank(message = "Домен сайта не был указан!")
    @Pattern(regexp = "^(?!\\-)(?:[a-zA-Z\\d\\-]{0,62}[a-zA-Z\\d]\\.){1,126}(?!\\d+)[a-zA-Z\\d]{1,63}$",
            message = "Неверно указан домен сайта! Возможно был поставлен лишний пробел в начале или в конце строки.")
    private String domain;

    @NotBlank(message = "URL не был указан!!!")
    @Pattern(regexp = "\\b(?:(?:https?):\\/\\/|www\\.)[-a-zA-Zа-яА-ЯёЁ0-9+&@#\\/%?=~_|!:,.;]*[-a-zA-Zа-яА-ЯёЁ0-9+&@#\\/%?=~_|]\\S",
            message = "URL введен не верно!!! Возможно был поставлен лишний пробел в начале либо в конце строки, либо некорректно указан один из протоколов(http,https://) передачи информации в интернете!")
    @UrlExists
    private String urlSite;
}
