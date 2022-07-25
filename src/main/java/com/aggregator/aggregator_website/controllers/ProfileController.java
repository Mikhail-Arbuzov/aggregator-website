package com.aggregator.aggregator_website.controllers;

import com.aggregator.aggregator_website.dto.UserDto;
import com.aggregator.aggregator_website.entities.User;
import com.aggregator.aggregator_website.services.DetailService;
import com.aggregator.aggregator_website.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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


    @GetMapping("/profile")
    public String getDetailUser(Model model){
        UserDto currentUser = getCurrentUserDto();
        model.addAttribute("currentUser", currentUser);
        return "profile";
    }

    @GetMapping("/profile/settings")
    public String getSettingsProfile(Model model,String message,String message1,String message2){
        UserDto user = getCurrentUserDto();
        model.addAttribute("user",user);
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



    @PutMapping("/profile/update-avatar")
    public String updateAvatar(@RequestParam("file") MultipartFile file, Model model){
        final String UPLOAD_PATH ="target\\classes\\static\\avatars\\";

        User actualCurrentUser = userService.getCurrentUser();
//        deleteOldAvatar(actualCurrentUser);

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

        String message2 ="Файл должен быть с расширением .jpg или.jpeg";
        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String jpg = ".jpg";
        String jpeg = ".jpeg";

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

//    private void deleteOldAvatar(User actualCurrentUser) {
//        final String PATH_FILE="src\\main\\resources\\static\\";
//        if(actualCurrentUser != null){
//            String avatar = actualCurrentUser.getDetail().getAvatarka();
//            if(!avatar.equals("avatars/M.jpg")){
//                try {
//                   Files.delete(Paths.get(PATH_FILE + avatar));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            else if(!avatar.equals("avatars/ty.jpg")){
//                try {
//                    Files.delete(Paths.get(PATH_FILE + avatar));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    private UserDto getCurrentUserDto(){
        User actualUser = userService.getCurrentUser();
        UserDto currentUser = new UserDto();
        if (actualUser !=null){
            currentUser= modelMapper.map(actualUser,UserDto.class);
        }

        return currentUser;
    }

}
