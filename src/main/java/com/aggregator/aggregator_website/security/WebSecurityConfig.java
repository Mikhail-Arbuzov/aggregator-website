package com.aggregator.aggregator_website.security;

import com.aggregator.aggregator_website.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthenticationProviderImplementation authenticationProvider;
    private final UserDetailsServiceImplementation userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfig (AuthenticationProviderImplementation authenticationProvider,
                              UserDetailsServiceImplementation userService){
        this.authenticationProvider= authenticationProvider;
        this.userService=userService;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers("/index","/css/**","/js/**","/fonts/**","/icons/**","/img/**","/avatars/**").permitAll()
//                .antMatchers(HttpMethod.GET,"/admin/**").hasRole(Role.ADMIN.name())
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/admin/**").authenticated()
                .antMatchers(HttpMethod.GET,"/profile/**").authenticated()
                .and()
                .formLogin()
                .loginPage("/user/login")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/allForPC")
                .failureForwardUrl("/user/perform_login?error=true")
                .and()
                .logout()
                .logoutUrl("/allForPC/logout")
                .logoutSuccessUrl("/allForPC")
                .and()
                .exceptionHandling().accessDeniedPage("/user/access-denied")
                .and()
                .csrf().disable();

    }
}
