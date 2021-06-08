package io.harness.topline.services.impl;

import io.harness.topline.daos.api.UserDao;
import io.harness.topline.models.User;
import io.harness.topline.services.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
  private final UserDao userDao;

  @Override
  public User createUser(@NotNull String principal, @NotNull String email,
                         @NotNull String name) {
    User user = User.builder()
                    .email(email)
                    .isActive(true)
                    .name(name)
                    .principal(principal)
                    .build();
    user = userDao.save(user);
    return user;
  }

  @Override
  public Optional<User> getUserByPrincipal(@NotNull String principal) {
    return userDao.getUserByPrincipalAndIsActive(principal, Boolean.TRUE);
  }

  @Override
  public Optional<User> getUserByPrincipal(@NotNull String principal,
                                           boolean includeDisabledProfiles) {
    if (includeDisabledProfiles)
      return userDao.getUserByPrincipal(principal);
    return getUserByPrincipal(principal);
  }

  @Override
  public Optional<User> disableAccount(@NotNull String principal) {
    Optional<User> userOptional = getUserByPrincipal(principal, true);
    if (!userOptional.isPresent())
      return Optional.empty();

    User user = userOptional.get();
    user.setActive(false);
    userDao.save(user);
    return Optional.of(user);
  }

  @Override
  public Optional<User> getLoggedInUser() {
    String principal = (String)SecurityUtils.getSubject()
                           .getPrincipals()
                           .getPrimaryPrincipal();
    return getUserByPrincipal(principal);
  }

  @Override
  public Set<User> saveAll(Set<User> users) {
    return userDao.saveAll(users);
  }

  @Override
  public Set<User> getUsersByPrincipal(Set<String> principalSet) {
    return userDao.getUsersByPrincipalAndIsActive(principalSet, true);
  }

  @Override
  public Set<User> getUsers(boolean isActive, boolean admin) {
    return userDao.getUsers(isActive, admin);
  }
}
