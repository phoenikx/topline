package io.harness.topline.daos.api;

import io.harness.topline.models.User;

import java.util.Optional;
import java.util.Set;

public interface UserDao {

  Optional<User> getUserByPrincipalAndIsActive(String principal,
                                               boolean isActive);

  User save(User user);

  Optional<User> getUserByPrincipal(String principal);

  Set<User> getUsersByPrincipalAndIsActive(Set<String> principalSet,
                                           boolean isActive);

  Set<User> saveAll(Set<User> users);

  Set<User> getUsers(boolean isActive);
}
