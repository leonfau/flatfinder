package de.leon.flatfinder.configuration;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "telegram")
@Data
public class TelegramConfigurationProperties {

    @NotBlank
    private String token;

    @NotBlank
    private String clientId;
}
