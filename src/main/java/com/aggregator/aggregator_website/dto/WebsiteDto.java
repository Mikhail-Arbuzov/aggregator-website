package com.aggregator.aggregator_website.dto;

import com.aggregator.aggregator_website.entities.Page;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebsiteDto {
    private Long id;
    private String iconSite;
    private String siteName;
    private String visits;
    private String timeOnSite;
    private double bounceRate;
    private int ratingInWorld;
    private int ratingInCountry;
    private String month;
    private String year;
    private Page page;
    private String section;
}
