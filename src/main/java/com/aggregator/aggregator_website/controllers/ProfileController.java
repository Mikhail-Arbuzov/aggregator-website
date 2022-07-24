package com.aggregator.aggregator_website.controllers;

import com.aggregator.aggregator_website.dto.UserDto;
import com.aggregator.aggregator_website.entities.User;
import com.aggregator.aggregator_website.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class ProfileController {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @GetMapping("/profile")
    public String getDetailUser(Model model){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        User actualUser = userRepository.findByUsername(username).orElse(null);
        UserDto currentUser = new UserDto();
        if (actualUser !=null){
            currentUser= modelMapper.map(actualUser,UserDto.class);
        }

        model.addAttribute("currentUser", currentUser);
        return "profile";

    }


}
