package com.aggregator.aggregator_website.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KeyboardController {
    @GetMapping("/allForPC/keyboard")
    public String getKeyboardPage(){
        return "equipments/keyboard";
    }
}
