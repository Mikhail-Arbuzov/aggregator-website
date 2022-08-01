package com.aggregator.aggregator_website.controllers;

import com.aggregator.aggregator_website.dto.EmailForRecoveryPasswordDto;
import com.aggregator.aggregator_website.dto.PasswordResetDto;
import com.aggregator.aggregator_website.dto.RegistrationRequest;
import com.aggregator.aggregator_website.dto.UserDto;
import com.aggregator.aggregator_website.entities.Email;
import com.aggregator.aggregator_website.entities.PasswordResetToken;
import com.aggregator.aggregator_website.entities.User;
import com.aggregator.aggregator_website.services.EmailService;
import com.aggregator.aggregator_website.services.UserService;
import com.aggregator.aggregator_website.services.exceptionregistration.EmailIsAlreadyThereException;
import com.aggregator.aggregator_website.services.exceptionregistration.UserNameIsAlreadyThereException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.mail.username}")
    private String username;

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

    //endpoint для перевода пользователя не имеющего роль админа на другую страницу
    @GetMapping("/access-denied")
    public String accessDenied(){
        return "error/error403";
    }

    //endpoint-ы для функционала - забыли пароль

    @GetMapping("/recoveryPass")
    public String recoveryPasswordPage(@ModelAttribute("recoveryPasswordDto") EmailForRecoveryPasswordDto recoveryPasswordDto){
        return "recovery";
    }

    @GetMapping("/reset-password")
    public String displayResetPasswordPage(@ModelAttribute("passwordResetDto") PasswordResetDto passwordResetDto,
                                           @RequestParam(required = false) String token,
                                           Model model){
        PasswordResetToken resetToken = userService.getPasswordResetToken(token);
        boolean isExpired = LocalTime.now().isAfter(resetToken.getExpiryTime());

        if(resetToken == null){
            model.addAttribute("error","Не удалось найти токен сброса пароля.");
        }
        else if(isExpired){
            model.addAttribute("error","Срок действия токена истек,он действует до 23:20:00 ч, пожалуйста, запросите новый сброс пароля после 00:00:00 ч");
        }

        else {
            model.addAttribute("token",resetToken.getToken());
        }

        return "newpassword";
    }

    @PostMapping("/recoveryPass")
    public String processRecoveryPassword( @Valid @ModelAttribute("recoveryPasswordDto") EmailForRecoveryPasswordDto recoveryPasswordDto,
                                          BindingResult bindingResult,
                                          HttpServletRequest request){

        if(bindingResult.hasErrors()){
            return "recovery";
        }

        User user = userService.findByUserEmail(recoveryPasswordDto.getEmail());
        if(user == null){
            bindingResult.rejectValue("email",null,"Учетная запись для этого адреса электронной почты не найдена");
            return "recovery";
        }

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(UUID.randomUUID().toString());
        passwordResetToken.setUser(user);
        LocalTime time = LocalTime.of(23,20,0);
        passwordResetToken.setExpiryTime(time);

        userService.saveToken(passwordResetToken);

        Email email = new Email();
        email.setForm(username);
        email.setTo(user.getDetail().getEmail());
        email.setSubject("Запрос на восстановление пароля");

        Map<String,Object> model = new HashMap<>();
        model.put("token",passwordResetToken);
        model.put("user",user);
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        model.put("resetUrl",url + "/user/reset-password?token="+ passwordResetToken.getToken());
        email.setModel(model);
        emailService.sendEmail(email);

        return "redirect:/user/recoveryPass?success";
    }

    @PatchMapping("/reset-password")
    public String handlePasswordReset(@Valid @ModelAttribute("passwordResetDto") PasswordResetDto passwordResetDto
            , BindingResult bindingResult, Model model){

        if(!confirmPassword(passwordResetDto).getDefaultMessage().isEmpty()){
            bindingResult.addError(confirmPassword(passwordResetDto));
        }
        if (bindingResult.hasErrors()){
           model.addAttribute("token",passwordResetDto.getToken());
            return "newpassword";
        }

        PasswordResetToken resetToken = userService.getPasswordResetToken(passwordResetDto.getToken());
        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(passwordResetDto.getPassword()));
        userService.changePassword(user);
        userService.deletePasswordResetToken(resetToken);

        return "redirect:/user/login?resetSuccess";

    }

    private ObjectError confirmPasswordValid(RegistrationRequest request){
        if(!request.getPassword().equals(request.getConfirmPassword())){
            return new ObjectError("RegistrationRequest","Подтверждение пароля не прошло!");
        }
        return new ObjectError("RegistrationRequest","");
    }

    private ObjectError confirmPassword(PasswordResetDto passwordResetDto){
        if(!passwordResetDto.getPassword().equals(passwordResetDto.getConfirmPassword())){
            return new ObjectError("PasswordResetDto","Подтверждение нового пароля не прошло!");
        }
        return new ObjectError("PasswordResetDto","");
    }
}
