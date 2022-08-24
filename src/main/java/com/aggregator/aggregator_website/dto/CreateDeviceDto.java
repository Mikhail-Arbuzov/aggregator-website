package com.aggregator.aggregator_website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDeviceDto {

    private String urlPage;

    @NotBlank(message = "Название устройства не указано!")
    //@Pattern(regexp = "^([^\\s].+[^@#$%^&()+=/*/\\/\\]\\[{}><|])?$",message = "Недопустимо указывание данных символов:~ @ # $ % ^ & * + = () <> [] {} \\ / | и пробела в начале строки.")
    @Pattern(regexp = "^[^\\s][^<>{}\\[\\]()%$~@#\\^&*+=\\|]*[^\\s]$",message = "Недопустимо указывание данных символов: ~ @ # $ % ^ & * + = () <> [] {} | и пробела в начале или в конце строки.")
    private String name;

    private MultipartFile file;

    @NotBlank(message = "Поле для ввода описания устройства не заполненно!")
    //@Pattern(regexp = "^([^\\s].+[^@#$%^&()+=/*/\\/\\]\\[{}><|])?$",message = "Недопустимо указывание данных символов:~ @ # $ % ^ & * + = () <> [] {} \\ / | и пробела в начале или в конце строки.")
    @Pattern(regexp = "^[^\\s][^<>{}\\[\\]()%$~@#\\^&*+=\\|]*[^\\s]$",message = "Недопустимо указывание данных символов: ~ @ # $ % ^ & * + = () <> [] {} | и пробела в начале или в конце строки.")
    private String description;


    @Min(value = 0, message = "Цена не может быть меньше нуля!")
    private double averagePrice;

    private String section;

    private String destination;


    @Pattern(regexp = "^(https?:\\/\\/)?(www\\.)?citilink\\.ru\\/[-a-zA-Zа-яА-ЯёЁ0-9+&@#\\/%?=~_|!:,.;]*[-a-zA-Zа-яА-ЯёЁ0-9+&@#\\/%?=~_|]+$",
            message = "Ссылка на 'Ситилинк' указана не верно!!!")
    private String citilinkURL;

    @Pattern(regexp = "^(https?:\\/\\/)?(www\\.)?regard\\.ru\\/[-a-zA-Zа-яА-ЯёЁ0-9+&@#\\/%?=~_|!:,.;]*[-a-zA-Zа-яА-ЯёЁ0-9+&@#\\/%?=~_|]+$",
            message = "Ссылка на 'Регард' указана не верно!!!")
    private String regardURL;

    @Pattern(regexp = "^(https?:\\/\\/)?(www\\.)?computermarket\\.ru\\/[-a-zA-Zа-яА-ЯёЁ0-9+&@#\\/%?=~_|!:,.;]*[-a-zA-Zа-яА-ЯёЁ0-9+&@#\\/%?=~_|]+$",
            message = "Ссылка на 'Компьютер маркет' указана не верно!!!")
    private String computerMarketURL;

    @Pattern(regexp = "^(https?:\\/\\/)?(www\\.)?quke\\.ru\\/[-a-zA-Zа-яА-ЯёЁ0-9+&@#\\/%?=~_|!:,.;]*[-a-zA-Zа-яА-ЯёЁ0-9+&@#\\/%?=~_|]+$",
            message = "Ссылка на 'Quke.ru' указана не верно!!!")
    private String qukeURL;

    @Pattern(regexp = "^(https?:\\/\\/)?(www\\.)?kns\\.ru\\/[-a-zA-Zа-яА-ЯёЁ0-9+&@#\\/%?=~_|!:,.;]*[-a-zA-Zа-яА-ЯёЁ0-9+&@#\\/%?=~_|]+$",
            message = "Ссылка на 'KNS' указана не верно!!!")
    private String knsURL;
}
