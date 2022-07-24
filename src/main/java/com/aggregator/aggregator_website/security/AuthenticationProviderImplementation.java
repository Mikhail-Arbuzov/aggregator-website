package com.aggregator.aggregator_website.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component

public class AuthenticationProviderImplementation implements AuthenticationProvider {
    private final UserDetailsServiceImplementation userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationProviderImplementation(UserDetailsServiceImplementation userDetailsService ){
        this.userDetailsService = userDetailsService;
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if(userDetailsService == null){
            throw new InternalAuthenticationServiceException("User service is null");
        }

        UserDetails user = userDetailsService.loadUserByUsername(username);

        if(user==null){
            throw new AuthenticationCredentialsNotFoundException("Такой пользователь не был найден");
        }

        if(passwordEncoder.matches(password,user.getPassword())){
            return new UsernamePasswordAuthenticationToken(user,authentication.getCredentials(),user.getAuthorities());
        }
        else{
            throw new AuthenticationServiceException("Не удалось аутентифицировать пользователя");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
