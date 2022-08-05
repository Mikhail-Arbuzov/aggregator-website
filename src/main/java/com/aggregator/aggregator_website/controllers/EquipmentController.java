package com.aggregator.aggregator_website.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EquipmentController {
    @GetMapping("/allForPC/processor")
    public String getProcessorsPage(){
        return "equipments/procesor";
    }
}
