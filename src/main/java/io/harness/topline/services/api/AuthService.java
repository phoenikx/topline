package io.harness.topline.services.api;

import io.harness.topline.enums.OAuthProvider;
import java.util.Optional;

public interface AuthService {
  Optional<String> login(String authCode, OAuthProvider oAuthProvider);

  boolean logout(String token);

  Optional<String> getConfig(OAuthProvider provider);

  void clearCachedAuthorizingInfo(String principal);
}
