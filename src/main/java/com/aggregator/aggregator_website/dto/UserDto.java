package com.aggregator.aggregator_website.dto;

import com.aggregator.aggregator_website.entities.Detail;
import com.aggregator.aggregator_website.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotBlank(message = "Не указан логин!")
    @Pattern(regexp = "^[a-zA-Z0-9]+$",message = "Возможно был поставлен лишний пробел в начале или в конце строки!Нужно указывать только цыфры и латинские символы!")
    private String username;
    @NotBlank(message = "Не указан пароль!")
    @Size(min = 5, message = "Пароль должен содержать минимум 5 символов!")
    private String password;
    private Set<Role> roles;
    private Detail detail;
}
