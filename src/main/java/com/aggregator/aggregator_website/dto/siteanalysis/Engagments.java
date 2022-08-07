package com.aggregator.aggregator_website.dto.siteanalysis;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Engagments {
    @JsonProperty("BounceRate")
    private String bounceRate;
    @JsonProperty("Month")
    private String month;
    @JsonProperty("Year")
    private String year;
    @JsonProperty("PagePerVisit")
    private String pagePerVisit;
    @JsonProperty("Visits")
    private String visits;
    @JsonProperty("TimeOnSite")
    private String timeOnSite;
}
