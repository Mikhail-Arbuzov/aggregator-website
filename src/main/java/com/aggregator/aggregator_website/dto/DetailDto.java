package com.aggregator.aggregator_website.dto;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class DetailDto {
    private Long id;
    private String firstName;
    private String secondName;
    private String email;
    private String avatarka;
    private String description;
    private String vkNetwork;
    private String classmatesNetwork;
    private String telegramNetwork;
}
