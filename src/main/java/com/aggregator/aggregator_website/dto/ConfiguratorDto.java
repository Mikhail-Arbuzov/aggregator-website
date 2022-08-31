package com.aggregator.aggregator_website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguratorDto {
    private Long id;
    private String logotype;
    private String siteName;
    private BigDecimal visits;
    private double bounceRate;
    private LocalTime timeOnSite;
    private String month;
    private String year;
    private String link;
}
