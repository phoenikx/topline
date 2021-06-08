package io.harness.topline.services.api;

import io.harness.topline.models.User;

import java.util.Optional;
import java.util.Set;

public interface UserService {

  User createUser(String principal, String email, String name);

  Optional<User> getUserByPrincipal(String principal);

  Optional<User> getUserByPrincipal(String principal, boolean disabled);

  Optional<User> disableAccount(String principal);

  Optional<User> getLoggedInUser();

  Set<User> saveAll(Set<User> users);

  Set<User> getUsersByPrincipal(Set<String> principalSet);

  Set<User> getUsers(boolean isActive, boolean admin);
}
