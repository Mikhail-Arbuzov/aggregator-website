package com.aggregator.aggregator_website.config;

import com.aggregator.aggregator_website.entities.Detail;
import com.aggregator.aggregator_website.entities.Role;
import com.aggregator.aggregator_website.entities.User;
import com.aggregator.aggregator_website.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Configuration
@AllArgsConstructor
public class AdminInitializer {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void postConstruct(){
        Optional<User> admin = userRepository.findByUsername("admin");
        User user = new User();
        Detail detail = new Detail();

        if(!admin.isPresent()){
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRoles(new HashSet<>(Arrays.asList(Role.ADMIN)));

            detail.setFirstName("Mikhail");
            detail.setSecondName("Arbuzov");
            detail.setAvatarka("avatars/M.jpg");
            detail.setDescription("Начинающий прогер");
            detail.setEmail("mihan.arbuzov@mail.ru");
            detail.setVkNetwork("https://vk.com/id328992012");
            detail.setClassmatesNetwork("https://ok.ru/");
            detail.setTelegramNetwork("https://t.me/telegram");

            user.setDetail(detail);
            userRepository.save(user);
        }
    }
}
