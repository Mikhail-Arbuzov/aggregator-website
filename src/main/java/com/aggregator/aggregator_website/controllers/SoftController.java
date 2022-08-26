package com.aggregator.aggregator_website.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SoftController {
    @GetMapping("/allForPC/soft")
    public String getSoftPage(){
        return "soft";
    }
}
