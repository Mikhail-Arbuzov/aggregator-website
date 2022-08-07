package com.aggregator.aggregator_website.dto.siteanalysis;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Map;

@Data
public class SiteAnalysisDto {
    @JsonProperty("SiteName")
    private String siteName;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("TopCountryShares")
    private List<CountryShare> topCountryShares;
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Engagments")
    private Engagments engagments;
    @JsonProperty("EstimatedMonthlyVisits")
    private Map<String,Long> estimatedMonthlyVisits;
    @JsonProperty("GlobalRank")
    private GlobalRank globalRank;
    @JsonProperty("CountryRank")
    private CountryRank countryRank;
    @JsonProperty("IsSmall")
    private boolean isSmall;
    @JsonProperty("TrafficSources")
    private TrafficSources trafficSources;
    @JsonProperty("Category")
    private String category;
    @JsonProperty("CategoryRank")
    private CategoryRank categoryRank;
    @JsonProperty("LargeScreenshot")
    private String largeScreenshot;
}
