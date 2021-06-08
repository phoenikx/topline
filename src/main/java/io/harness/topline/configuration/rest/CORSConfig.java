package io.harness.topline.configuration.rest;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cors")
@Data
public class CORSConfig {
    private String accessControlAllowOrigin;
    private String accessControlAllowMethods;
    private String accessControlMaxAge;
    private String accessControlAllowHeaders;
}
