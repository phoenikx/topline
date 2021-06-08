package io.harness.topline.daos.api;

import io.harness.topline.models.BearerToken;
import java.util.Optional;

public interface BearerTokenDao {
  Optional<BearerToken> getBearerToken(String token);

  BearerToken saveToken(BearerToken bearerToken);

  void deleteToken(BearerToken bearerToken);
}
