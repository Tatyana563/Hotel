package com.hotel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableScheduling
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("main");
        registry.addViewController("/edit").setViewName("hotel_edit");
        registry.addViewController("/new").setViewName("hotel_creation");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/registration").setViewName("registration");
    }
}
