package com.aggregator.aggregator_website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SoftwareDto {
    private Long id;
    private String icon;
    private String site;
    private String img;
    private String title;
    private String text;
    private String link;
}
