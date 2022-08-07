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
public class CategoryRank {
    @JsonProperty("Rank")
    private String rank;
    @JsonProperty("Category")
    private String category;
}
