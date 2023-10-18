package com.hotel.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Duration;

@Data
@Validated
@ConfigurationProperties(prefix = "registration")
public class RegistrationProperties {
    @NotEmpty
    private String baseUrl;
    @NotNull
    private Duration tokenExpiration;
}
