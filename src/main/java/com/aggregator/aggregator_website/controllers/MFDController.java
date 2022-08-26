package com.aggregator.aggregator_website.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MFDController {
    @GetMapping("/allForPC/mfd")
    public String getMFDPage(){
        return "equipments/mfd";
    }
}
