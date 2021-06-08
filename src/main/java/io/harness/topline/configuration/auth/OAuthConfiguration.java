package io.harness.topline.configuration.auth;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuthConfiguration {
  @Bean
  OAuthClient getOAuthClient() {
    return new OAuthClient(new URLConnectionClient());
  }
}
