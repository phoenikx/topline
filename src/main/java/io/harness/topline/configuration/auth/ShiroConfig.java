package io.harness.topline.configuration.auth;

import io.harness.topline.configuration.BearerTokenRealm;
import io.harness.topline.configuration.filters.TokenBasedAuthenticatingFilter;
import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "shiro")
public class ShiroConfig {
  private static final String AUTHORIZATION_CACHE_NAME =
      "SHIRO_AUTHORIZATION_CACHE";
  @Autowired private SessionStorageEvaluator sessionStorageEvaluator;
  private boolean cachingEnabled;
  private boolean authenticationCachingEnabled;
  private boolean authorizationCachingEnabled;

  @PostConstruct
  public void setProperties() {
    ((DefaultSessionStorageEvaluator)sessionStorageEvaluator)
        .setSessionStorageEnabled(false);
  }

  @Bean
  public Realm realm() {
    BearerTokenRealm realm = new BearerTokenRealm();
    realm.setCachingEnabled(cachingEnabled);
    realm.setAuthenticationCachingEnabled(authenticationCachingEnabled);
    realm.setAuthorizationCachingEnabled(authorizationCachingEnabled);
    realm.setAuthorizationCacheName(AUTHORIZATION_CACHE_NAME);
    return realm;
  }

  @Bean
  public FilterRegistrationBean filterRegistrationBean() {
    FilterRegistrationBean filterRegistrationBean =
        new FilterRegistrationBean();
    filterRegistrationBean.setFilter(authenticationFilter());
    filterRegistrationBean.setEnabled(
        false); // we enable it in shiroFilterChainDefinition()
    return filterRegistrationBean;
  }

  @Bean(name = "authenticationFilter")
  public Filter authenticationFilter() {
    return new TokenBasedAuthenticatingFilter();
  }

  @Bean
  public ShiroFilterChainDefinition shiroFilterChainDefinition() {
    DefaultShiroFilterChainDefinition chainDefinition =
        new DefaultShiroFilterChainDefinition();
    chainDefinition.addPathDefinition("/error", "anon");
    chainDefinition.addPathDefinition("/v3/api-docs", "anon");
    chainDefinition.addPathDefinition("/v3/api-docs/*", "anon");
    chainDefinition.addPathDefinition("/v3/api-docs.yaml", "anon");
    chainDefinition.addPathDefinition("/swagger-ui.html", "anon");
    chainDefinition.addPathDefinition("/swagger-ui/*", "anon");
    chainDefinition.addPathDefinition("/webjars/**", "anon");
    chainDefinition.addPathDefinition("/auth/oauth/**", "anon");
    chainDefinition.addPathDefinition("/health", "anon");
    chainDefinition.addPathDefinition("/**", "authenticationFilter");
    return chainDefinition;
  }
}
