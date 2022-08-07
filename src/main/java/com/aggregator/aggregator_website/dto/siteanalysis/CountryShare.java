package com.aggregator.aggregator_website.dto.siteanalysis;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryShare {
    @JsonProperty("Value")
    private BigDecimal value;
    @JsonProperty("Country")
    private int country;
}
