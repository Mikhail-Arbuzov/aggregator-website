package com.aggregator.aggregator_website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {
    private Long id;
    private String title;
    private String imageUrl;
    private String text;
    private String link;
}
