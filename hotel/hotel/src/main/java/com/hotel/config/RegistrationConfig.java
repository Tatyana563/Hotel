package com.hotel.config;

import com.hotel.config.properties.RegistrationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({RegistrationProperties.class})
public class RegistrationConfig {
}
