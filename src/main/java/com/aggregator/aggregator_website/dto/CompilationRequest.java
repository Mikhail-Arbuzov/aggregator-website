package com.aggregator.aggregator_website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompilationRequest {
    private String processorName;
    private String motherboardName;
    private String cpuCoolerName;
    private String ramName;
    private String hddName;
    private String sddName;
    private String videoCardName;
    private String powerSupplyName;
    private String coolerName;
    private String corpusName;
    private String mouseName;
    private String keyboardName;
    private String monitorName;
    private String mfdName;
    private String destiny;
}
