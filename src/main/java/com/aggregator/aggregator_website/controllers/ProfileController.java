package com.aggregator.aggregator_website.controllers;

import com.aggregator.aggregator_website.dto.*;
import com.aggregator.aggregator_website.entities.Detail;
import com.aggregator.aggregator_website.entities.User;
import com.aggregator.aggregator_website.services.DetailService;
import com.aggregator.aggregator_website.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@AllArgsConstructor
public class ProfileController {
    private final ModelMapper modelMapper;
//    private final UserRepository userRepository;
    private final UserService userService;
    private final DetailService detailService;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/profile")
    public String getDetailUser(Model model){
        UserDto currentUser = getCurrentUserDto();
        model.addAttribute("currentUser", currentUser);
        return "profile";
    }

    @GetMapping("/profile/settings")
    public String getSettingsProfile(Model model, String message, String message1, String message2){
        UserDto user = getCurrentUserDto();
        model.addAttribute("user",user);

        FullNameUserRequest fullNameUserRequest = new FullNameUserRequest();
        model.addAttribute("fullNameUserRequest",fullNameUserRequest);

        DescriptionUserRequest descriptionUser =  new DescriptionUserRequest();
        model.addAttribute("descriptionUser",descriptionUser);

        NewEmailUserRequest newEmailUser = new NewEmailUserRequest();
        model.addAttribute("newEmailUser",newEmailUser);

        SocialNetworkUserRequest socialNetworkUser = new SocialNetworkUserRequest();
        model.addAttribute("socialNetworkUser",socialNetworkUser);

        if(message !=null){
            model.addAttribute("message",message);
        }

        if(message1 !=null){
            model.addAttribute("message1",message1);
        }

        if(message2 !=null){
            model.addAttribute("message2",message2);
        }
        return "profile-settings";
    }

    @GetMapping("/profile/changePassword")
    public String getChangePassword(@ModelAttribute("changePasswordRequest") ChangePasswordRequest changePasswordRequest){
        return "profile-changepass";
    }

    @PutMapping("/profile/update-avatar")
    public String updateAvatar(@RequestParam("file") MultipartFile file, Model model){
        final String UPLOAD_PATH ="target\\classes\\static\\avatars\\";

        User actualCurrentUser = userService.getCurrentUser();

        String message = "Файл не был выбран";
        if(file.isEmpty()){
            model.addAttribute("message",message);
            return "redirect:/profile/settings";
        }

//        double filebyte = file.getSize();
        String message1 = "Размер файла должен быть не более 1 МБ";
        double megabytes = file.getSize() * 0.00000095367432;

        if(megabytes >= 1 || file.getSize() >= 1048576){
            model.addAttribute("message1",message1);
            return "redirect:/profile/settings";
        }

        String message2 ="Файл должен быть с расширением .jpg";
        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf("."));
//        String jpg = ".jpg";
//        String jpeg = ".jpeg";

        if (!extension.equals(".jpg")){
            model.addAttribute("message2",message2);
            return "redirect:/profile/settings";
        }


        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_PATH + actualCurrentUser.getUsername() + extension);
            Files.write(path,bytes);
            actualCurrentUser.getDetail().setAvatarka("avatars/"+ actualCurrentUser.getUsername() + extension);
            detailService.updateAvatar(actualCurrentUser.getDetail());
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        return "redirect:/profile/settings";
    }

    @PutMapping("/profile/update-FullName")
    public String updateFullNameUser(@Valid @ModelAttribute("fullNameUserRequest") FullNameUserRequest fullNameUserRequest, BindingResult bindingResult,Model model){
        if(bindingResult.hasErrors()){
            UserDto user = getCurrentUserDto();
            model.addAttribute("user",user);

            DescriptionUserRequest descriptionUser =  new DescriptionUserRequest();
            model.addAttribute("descriptionUser",descriptionUser);

            NewEmailUserRequest newEmailUser = new NewEmailUserRequest();
            model.addAttribute("newEmailUser",newEmailUser);

            SocialNetworkUserRequest socialNetworkUser = new SocialNetworkUserRequest();
            model.addAttribute("socialNetworkUser",socialNetworkUser);
            return "profile-settings";
        }
        else {
            User actualUser = userService.getCurrentUser();
            UserDto userDto = new UserDto();
            DetailDto detailDto = new DetailDto();
            detailDto.setId(actualUser.getDetail().getId());
            detailDto.setFirstName(fullNameUserRequest.getFirstName());
            detailDto.setSecondName(fullNameUserRequest.getSecondName());
            Detail detail = modelMapper.map(detailDto,Detail.class);
            userDto.setDetail(detail);
            detailService.updateFullNameUser(userDto.getDetail());
            return "redirect:/profile";
        }
    }

    @PutMapping("/profile/update-description")
    public String updateDescriptionUser(@Valid @ModelAttribute("descriptionUser") DescriptionUserRequest descriptionUser,BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            UserDto user = getCurrentUserDto();
            model.addAttribute("user",user);

            FullNameUserRequest fullNameUserRequest = new FullNameUserRequest();
            model.addAttribute("fullNameUserRequest",fullNameUserRequest);

            NewEmailUserRequest newEmailUser = new NewEmailUserRequest();
            model.addAttribute("newEmailUser",newEmailUser);

            SocialNetworkUserRequest socialNetworkUser = new SocialNetworkUserRequest();
            model.addAttribute("socialNetworkUser",socialNetworkUser);
            return "profile-settings";
        }
        else {
            User actualUser1 = userService.getCurrentUser();
            UserDto userDto1 = new UserDto();
            DetailDto detailDto1 = new DetailDto();
            detailDto1.setId(actualUser1.getDetail().getId());
            detailDto1.setDescription(descriptionUser.getDescription().trim());
            Detail detail = modelMapper.map(detailDto1,Detail.class);
            userDto1.setDetail(detail);
            detailService.updateDescriptionUser(userDto1.getDetail());
            return "redirect:/profile";
        }

    }

    @PutMapping("/profile/update-email")
    public String updateEmailUser(@Valid @ModelAttribute("newEmailUser") NewEmailUserRequest newEmailUser,BindingResult bindingResult,Model model){
        if(bindingResult.hasErrors()){
            UserDto user = getCurrentUserDto();
            model.addAttribute("user",user);

            FullNameUserRequest fullNameUserRequest = new FullNameUserRequest();
            model.addAttribute("fullNameUserRequest",fullNameUserRequest);

            DescriptionUserRequest descriptionUser =  new DescriptionUserRequest();
            model.addAttribute("descriptionUser",descriptionUser);

            SocialNetworkUserRequest socialNetworkUser = new SocialNetworkUserRequest();
            model.addAttribute("socialNetworkUser",socialNetworkUser);
            return "profile-settings";
        }
        else {
            User actualUser2 = userService.getCurrentUser();
            UserDto userDto2 = new UserDto();
            DetailDto detailDto2 = new DetailDto();
            detailDto2.setId(actualUser2.getDetail().getId());
            detailDto2.setEmail(newEmailUser.getEmail().trim());
            Detail detail = modelMapper.map(detailDto2,Detail.class);
            userDto2.setDetail(detail);
            detailService.updateEmailUser(userDto2.getDetail());
            return "redirect:/profile";
        }

    }

    @PutMapping("/profile/update-socNetwork")
    public  String updateSocNetworkUser(@Valid @ModelAttribute("socialNetworkUser") SocialNetworkUserRequest socialNetworkUser, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            FullNameUserRequest fullNameUserRequest = new FullNameUserRequest();
            model.addAttribute("fullNameUserRequest",fullNameUserRequest);

            DescriptionUserRequest descriptionUser =  new DescriptionUserRequest();
            model.addAttribute("descriptionUser",descriptionUser);

            NewEmailUserRequest newEmailUser = new NewEmailUserRequest();
            model.addAttribute("newEmailUser",newEmailUser);

            UserDto user = getCurrentUserDto();
            model.addAttribute("user",user);

            return "profile-settings";
        }
        else{
            User actualUser3 = userService.getCurrentUser();
            UserDto userDto3 = new UserDto();
            DetailDto detailDto3 = new DetailDto();
            detailDto3.setId(actualUser3.getDetail().getId());
            detailDto3.setVkNetwork(socialNetworkUser.getVkNetwork().trim());
            detailDto3.setClassmatesNetwork(socialNetworkUser.getClassmatesNetwork().trim());
            detailDto3.setTelegramNetwork("https://t.me/" + socialNetworkUser.getTelegramNetwork());
            Detail detail = modelMapper.map(detailDto3,Detail.class);
            userDto3.setDetail(detail);
            detailService.updateSocNetworkUser(userDto3.getDetail());
            return "redirect:/profile";
        }
    }


    @DeleteMapping("/profile/deleteUser")
    public String deleteUserProfile(){
        User userCurrent = userService.getCurrentUser();
        String avatar = userCurrent.getDetail().getAvatarka();

        if(userService.deleteUserProfile(userCurrent.getId())){
            deleteOldAvatar(avatar);
        }
        return "redirect:/allForPC/logout";
    }

    @PatchMapping("/profile/change-pass")
    public String changePassword(@Valid @ModelAttribute("changePasswordRequest") ChangePasswordRequest changePasswordRequest,
                                 BindingResult bindingResult,Model model){
        if(bindingResult.hasErrors()){
            return "profile-changepass";
        }
        User userCurrent1 = userService.getCurrentUser();

        if(passwordEncoder.matches(changePasswordRequest.getCurrentPass(),userCurrent1.getPassword()) &&
            changePasswordRequest.getNewPass().equals(changePasswordRequest.getConfirmPass())){
            userCurrent1.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPass()));
            userService.changePassword(userCurrent1);
            String successMessage ="Пароль успешно изменен.";
            model.addAttribute("successMessage",successMessage);
        }
        else if(!passwordEncoder.matches(changePasswordRequest.getCurrentPass(),userCurrent1.getPassword())){
            String errormessage = "Введен не верный пароль!";
            FieldError fieldError = new FieldError("changePasswordRequest","currentPass",errormessage);
            bindingResult.addError(fieldError);
        }
        else if(!changePasswordRequest.getNewPass().equals(changePasswordRequest.getConfirmPass())){
            String errormessage2 = "Подтверждение нового пароля не прошло!";
            FieldError fieldError = new FieldError("changePasswordRequest","confirmPass",errormessage2);
            bindingResult.addError(fieldError);
        }
        return "profile-changepass";
    }

    private void deleteOldAvatar(String avatar) {
        final String PATH_FILE="target\\classes\\static\\";

        if(avatar.equals("avatars/M.jpg")){
            return;
        }
        else if(avatar.equals("avatars/ty.jpg")){
            return;
        }
        else {
            try {
                Files.delete(Paths.get(PATH_FILE + avatar));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private UserDto getCurrentUserDto(){
        User actualUser = userService.getCurrentUser();
        UserDto currentUser = new UserDto();
        if (actualUser !=null){
            currentUser= modelMapper.map(actualUser,UserDto.class);
        }

        return currentUser;
    }

}
