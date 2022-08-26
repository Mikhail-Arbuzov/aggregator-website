package com.aggregator.aggregator_website.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AntivirusSoftController {
    @GetMapping("/allForPC/antivirus-soft")
    public String getAntivirusSoftPage(){
        return "antivirus-soft";
    }
}
