package io.harness.topline.daos.impl;

import io.harness.topline.daos.api.BearerTokenDao;
import io.harness.topline.models.BearerToken;
import io.harness.topline.repositories.mongo.BearerTokenRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class BearerTokenDaoImpl implements BearerTokenDao {
  @Autowired private BearerTokenRepository bearerTokenRepository;

  @Override
  public Optional<BearerToken> getBearerToken(String token) {
    return bearerTokenRepository.findByToken(token);
  }

  @Override
  public BearerToken saveToken(BearerToken bearerToken) {
    return bearerTokenRepository.save(bearerToken);
  }

  @Override
  public void deleteToken(BearerToken bearerToken) {
    bearerTokenRepository.delete(bearerToken);
  }
}
