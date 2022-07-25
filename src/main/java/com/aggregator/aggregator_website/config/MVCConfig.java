package com.aggregator.aggregator_website.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MVCConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry
                .addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        registry
                .addResourceHandler("allForPC/**")
                .addResourceLocations("classpath:/static/");
        registry
                .addResourceHandler("user/**")
                .addResourceLocations("classpath:/static/");
        registry
                .addResourceHandler("user/registration/**")
                .addResourceLocations("classpath:/static/");
        registry
                .addResourceHandler("profile/**")
                .addResourceLocations("classpath:/static/");
    }
}
