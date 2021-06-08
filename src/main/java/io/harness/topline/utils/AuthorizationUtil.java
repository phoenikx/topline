package io.harness.topline.utils;

import java.util.Optional;

public class AuthorizationUtil {
  private AuthorizationUtil() {}

  public static Optional<String>
  getAuthorizationTokenFromHeader(String authorizationHeader) {
    String[] authorizationHeaderSplit = authorizationHeader.split(" ");
    if (authorizationHeaderSplit.length < 2) {
      return Optional.empty();
    }
    return Optional.ofNullable(authorizationHeaderSplit[1]);
  }
}
