package com.aggregator.aggregator_website.controllers;

import com.aggregator.aggregator_website.exceptionhandlers.notifications.ErrorMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

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

    @GetMapping("/allForPC/error")
    public  String getErrorPage(@ModelAttribute("errormessage") ErrorMessage errorMessage){
        return "error/allerror";
    }
}
