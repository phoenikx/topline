package io.harness.topline.configuration.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("oauth.google")
@Data
public class GoogleOAuthConfig {
  private String scope;
  private String responseType;
  private String clientId;
  private String clientSecret;
  private String redirectUrl;
  private String profileUrl;
  private String tokenUrl;
}
