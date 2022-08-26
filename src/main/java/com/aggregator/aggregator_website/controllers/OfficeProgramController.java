package com.aggregator.aggregator_website.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OfficeProgramController {
    @GetMapping("/allForPC/officeProgram")
    public String getOfficeProgramPage(){
        return "office-program";
    }
}
