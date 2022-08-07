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
public class TrafficSources {
    @JsonProperty("Social")
    private BigDecimal social;
    @JsonProperty("Paid Referrals")
    private BigDecimal paidReferrals;
    @JsonProperty("Mail")
    private BigDecimal mail;
    @JsonProperty("Referrals")
    private BigDecimal referrals;
    @JsonProperty("Search")
    private BigDecimal search;
    @JsonProperty("Direct")
    private BigDecimal direct;
}
