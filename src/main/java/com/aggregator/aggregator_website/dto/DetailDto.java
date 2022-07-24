package com.aggregator.aggregator_website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
