package io.harness.topline.repositories.mongo;

import io.harness.topline.models.BearerToken;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BearerTokenRepository
    extends MongoRepository<BearerToken, String> {
  Optional<BearerToken> findByToken(String token);
}
