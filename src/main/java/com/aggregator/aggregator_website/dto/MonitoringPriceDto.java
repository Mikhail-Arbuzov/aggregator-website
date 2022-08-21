package com.aggregator.aggregator_website.dto;

import com.aggregator.aggregator_website.entities.Device;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonitoringPriceDto {
    private Long id;
    private String logoSite;
    private String siteName;
    private String price;
    private String link;
    private Device device;
}
