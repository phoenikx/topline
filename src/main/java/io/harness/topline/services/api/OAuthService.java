package io.harness.topline.services.api;

import io.harness.topline.enums.OAuthProvider;
import io.harness.topline.models.GoogleOAuthUserProfile;

public interface OAuthService {
  GoogleOAuthUserProfile
  fetchProfileFromOAuthProvider(String authCode, OAuthProvider oAuthProvider);
}
