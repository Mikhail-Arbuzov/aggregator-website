package com.aggregator.aggregator_website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InfoTrafficWebsite {
    private String visits;
    private double bounceRate;
    private int ratingInWorld;
    private int ratingInCountry;
    private String timeOnSite;
    private String month;
    private String year;
    private Long id;
}
