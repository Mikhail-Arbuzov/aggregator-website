package com.aggregator.aggregator_website.services;

import com.aggregator.aggregator_website.dto.DetailDto;
import com.aggregator.aggregator_website.dto.UserDto;
import com.aggregator.aggregator_website.entities.Detail;
import com.aggregator.aggregator_website.entities.Role;
import com.aggregator.aggregator_website.entities.User;
import com.aggregator.aggregator_website.repository.DetailRepository;
import com.aggregator.aggregator_website.repository.UserRepository;
import com.aggregator.aggregator_website.services.exceptionregistration.EmailIsAlreadyThereException;
import com.aggregator.aggregator_website.services.exceptionregistration.UserNameIsAlreadyThereException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private DetailRepository detailRepository;
    private final DetailService detailService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void addUser(String username, String email, String password) throws UserNameIsAlreadyThereException, EmailIsAlreadyThereException {
        User user = userRepository.findByUsername(username).orElse(null);
        if(user !=null){
            throw new UserNameIsAlreadyThereException("Пользователь с таким логином уже есть!");
        }
        Detail detail = detailRepository.findByEmail(email).orElse(null);
        if(detail !=null){
            throw new EmailIsAlreadyThereException("С таким email'ом уже зарегистрирован пользователь!");
        }

        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setPassword(passwordEncoder.encode(password));
        userDto.setRoles(new HashSet<>(Arrays.asList(Role.USER)));
        DetailDto detailDto = new DetailDto();
        detailDto.setFirstName("Имя");
        detailDto.setSecondName("Фамилия");
        detailDto.setEmail(email);
        detailDto.setAvatarka("avatars/ty.jpg");
        detailDto.setDescription("Укажите краткую информацию о себе и своих интересах");
        detailDto.setVkNetwork("https://vk.com/");
        detailDto.setClassmatesNetwork("https://ok.ru/");
        detailDto.setTelegramNetwork("https://t.me/telegram");

        detail = modelMapper.map(detailDto,Detail.class);

        userDto.setDetail(detail);
        user = modelMapper.map(userDto,User.class);
        userRepository.save(user);
    }

    @Transactional
    public boolean deleteUserProfile(Long id){
        if(userRepository.findById(id).isPresent()){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public User getCurrentUser(){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        User currentUser = userRepository.findByUsername(username).orElse(null);
        return currentUser;
    }


    @Transactional
    public void changePassword(User user) {
        userRepository.save(user);
    }
}
