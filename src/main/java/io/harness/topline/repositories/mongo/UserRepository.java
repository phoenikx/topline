package io.harness.topline.repositories.mongo;

import io.harness.topline.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> getByEmail(String email);

  Optional<User> findByPrincipalAndIsActive(String principal, boolean isActive);

  Optional<User> findByPrincipal(String principal);

  @Query(value = "{'principal': {'$in': ?0}, 'isActive': ?1}")
  Set<User> findAllByPrincipalAndIsActive(Set<String> principalSet,
                                          boolean isActive);

  @Query("{'isActive': ?0}") Set<User> findAllByIsActive(boolean isActive);
}
