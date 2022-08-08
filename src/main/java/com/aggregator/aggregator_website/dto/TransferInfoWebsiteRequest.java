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
public class TransferInfoWebsiteRequest {
    private String dataTransferPage;
    private String section;

    @NotBlank(message = "Домен сайта не был указан!")
    @Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9-]{1,61}[a-zA-Z0-9](?:\\.[a-zA-Z]{2,})+$",
            message = "Неверно указан домен сайта!Возможно был поставлен лишний пробел в начале или в конце строки.")
    private String domainSite;

    private String urlSite;
}
