package com.aggregator.aggregator_website.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OperativeController {
    @GetMapping("/allForPC/operative")
    public String getOperativeController(){
        return "operative";
    }
}
