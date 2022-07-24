package com.aggregator.aggregator_website.controllers;

import com.aggregator.aggregator_website.dto.RegistrationRequest;
import com.aggregator.aggregator_website.dto.UserDto;
import com.aggregator.aggregator_website.services.UserService;
import com.aggregator.aggregator_website.services.exceptionregistration.EmailIsAlreadyThereException;
import com.aggregator.aggregator_website.services.exceptionregistration.UserNameIsAlreadyThereException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String signInPage(@ModelAttribute("user") UserDto user){
        return "login";
    }

    @GetMapping("/registration")
    public String registrationUser(@ModelAttribute("registrationRequest")RegistrationRequest request){
        return "registration";
    }


    @PostMapping("/perform_login")
    public String loginUser(@Valid @ModelAttribute("user") UserDto user,
                            BindingResult bindingResult,Model model, String error){
        if(bindingResult.hasErrors()){
            return "login";
        }

        if(error !=null){
            model.addAttribute("error",true);
        }
        return "login";
    }

    @PostMapping("/registration/create")
    public String createUser(@Valid @ModelAttribute("registrationRequest")RegistrationRequest request,
                             BindingResult bindingResult,Model model){

        if(!confirmPasswordValid(request).getDefaultMessage().isEmpty()){
            bindingResult.addError(confirmPasswordValid(request));
        }
        if(bindingResult.hasErrors()){
            return "registration";
        }

        try{
            userService.addUser(request.getLogin(),request.getEmail(),request.getConfirmPassword());
        }
        catch (UserNameIsAlreadyThereException ex){
            FieldError fieldError = new FieldError("request","login", ex.getMessage());
            bindingResult.addError(fieldError);
            return "registration";
        }
        catch (EmailIsAlreadyThereException ex){
            FieldError fieldError2 = new FieldError("request","email", ex.getMessage());
            bindingResult.addError(fieldError2);
            return "registration";
        }

        String successfully = "Регистрация прошла успешно!";
        model.addAttribute("successfully",successfully);
        return "registration";
    }

    @GetMapping("/access-denied")
    public String accessDenied(){
        return "error/error403";
    }

    @GetMapping("/recoveryPass")
    public String recoveryPasswordPage(){
        return "recovery";
    }

    private ObjectError confirmPasswordValid(RegistrationRequest request){
        if(!request.getPassword().equals(request.getConfirmPassword())){
            return new ObjectError("RegistrationRequest","Подтверждение пароля не прошло!");
        }
        return new ObjectError("RegistrationRequest","");
    }
}
