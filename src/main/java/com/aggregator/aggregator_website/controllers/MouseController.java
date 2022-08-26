package com.aggregator.aggregator_website.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MouseController {
    @GetMapping("/allForPC/mouse")
    public String getMousePage(){
        return "equipments/mouse";
    }
}
