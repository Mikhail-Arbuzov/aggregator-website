package com.aggregator.aggregator_website.controllers;

import com.aggregator.aggregator_website.dto.RegistrationRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("registrationRequest")
    public RegistrationRequest getRegistration(){
        return new RegistrationRequest();
    }
}
