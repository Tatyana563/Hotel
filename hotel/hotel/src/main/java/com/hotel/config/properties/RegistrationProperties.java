package com.hotel.config.properties;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


import java.time.Duration;

@Data
@Validated
@ConfigurationProperties(prefix = "registration")
public class RegistrationProperties {
    @NotEmpty
    private String baseUrl;
    @NotNull
    private Duration tokenExpiration;
    @NotNull
    private Duration tokenTimeLeftToRenew;
    private Duration requestRetryDuration;
}
