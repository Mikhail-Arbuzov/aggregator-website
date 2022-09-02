package com.aggregator.aggregator_website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDto {
    private String processorBrand;
    private double processorPrice;

    private String motherboardBrand;
    private double motherboardPrice;

    private String sddBrand;
    private double sddPrice;

    private String hddBrand;
    private double hddPrice;

    private String cpuCoolerBrand;
    private double cpuCoolerPrice;

    private String coolerBrand;
    private double coolerPrice;

    private String ramBrand;
    private double ramPrice;

    private String videoCardBrand;
    private double videoCardPrice;

    private String powerSupplyBrand;
    private double powerSupplyPrice;

    private String corpusBrand;
    private double corpusPrice;

    private String mouseBrand;
    private double mousePrice;

    private String monitorBrand;
    private double monitorPrice;

    private String keyboardBrand;
    private double keyboardPrice;

    private String mfdBrand;
    private double mfdPrice;

    private String destiny;
}
