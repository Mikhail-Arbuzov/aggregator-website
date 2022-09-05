package com.aggregator.aggregator_website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AntivirusSoftDto {
    private Long id;
    private String imgURL;
    private String title;
    private String link;
}
