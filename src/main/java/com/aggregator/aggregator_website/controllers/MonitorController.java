package com.aggregator.aggregator_website.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MonitorController {
    @GetMapping("/allForPC/monitor")
    public String getMonitorPage(){
        return "equipments/monitor";
    }
}
