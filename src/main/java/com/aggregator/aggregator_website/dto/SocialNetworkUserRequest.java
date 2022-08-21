package com.aggregator.aggregator_website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SocialNetworkUserRequest {
    @NotBlank(message = "Поле для ввода 'ВК' незаполнено!")
//    @Pattern(regexp = "\\b(?:(?:https):\\/\\/vk.com\\/[^.]|)[-a-zA-Zа-яА-ЯёЁ0-9+&@#\\/%?=~_|!:,.;]*[-a-zA-Zа-яА-ЯёЁ0-9+&@#\\/%?=~_|]\\S",
//            message = "Ссылка на 'вк' указана не верно!!!")
    @Pattern(regexp = "^(https?:\\/\\/)?(www\\.)?vk\\.com\\/[-a-zA-Zа-яА-ЯёЁ0-9+&@#\\/%?=~_|!:,.;]*[-a-zA-Zа-яА-ЯёЁ0-9+&@#\\/%?=~_|]+$",
            message = "Ссылка на 'вк' указана не верно!!!")
    private String vkNetwork;

    @NotBlank(message = "Поле для ввода 'Одноклассники' незаполнено!")
//    @Pattern(regexp = "\\b(?:(?:https):\\/\\/ok.ru\\/[^.]|)[-a-zA-Zа-яА-ЯёЁ0-9+&@#\\/%?=~_|!:,.;]*[-a-zA-Zа-яА-ЯёЁ0-9+&@#\\/%?=~_|]\\S",
//            message = "Ссылка на 'одноклассники' указана не верно!!!")
    @Pattern(regexp = "^(https?:\\/\\/)?(www\\.)?ok\\.ru\\/[-a-zA-Zа-яА-ЯёЁ0-9+&@#\\/%?=~_|!:,.;]*[-a-zA-Zа-яА-ЯёЁ0-9+&@#\\/%?=~_|]+$",
              message = "Ссылка на 'одноклассники' указана не верно!!!")
    private String classmatesNetwork;

    @NotBlank(message = "Поле для ввода 'Телеграм' незаполнено!")
    @Pattern(regexp = "^[a-z0-9]+$",message = "Возможно был поставлен лишний пробел в начале или в конце строки! Нужно указывать только латинские символы в нижнем регистре и цифры!")
    @Size(min = 5, message = "nickname должен содержать минимум 5 символов!")
    private String telegramNetwork;
}
