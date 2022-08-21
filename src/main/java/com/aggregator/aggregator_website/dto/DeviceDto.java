package com.aggregator.aggregator_website.dto;

import com.aggregator.aggregator_website.entities.MonitoringPrice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDto {
    private Long id;
    private String name;
    private String image;
    private String description;
    private double averagePrice;
    private String destination;
    private String section;
    private List<MonitoringPrice> monitoringPrices;
}
