package com.aggregator.aggregator_website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemSourceDto {
    private Long id;
    private String logotype;
    private String domain;
    private String image;
    private String title;
    private String text;
    private String link;
}
