package com.aggregator.aggregator_website.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String getStartWebsite(){
        return "redirect:/allForPC";
    }


    @GetMapping("/allForPC")
    public String startPage(){
        return "index";
    }
}
