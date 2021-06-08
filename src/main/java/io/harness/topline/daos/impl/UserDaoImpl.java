package io.harness.topline.daos.impl;

import io.harness.topline.daos.api.UserDao;
import io.harness.topline.models.User;
import io.harness.topline.repositories.mongo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao {
  @Autowired private UserRepository userRepository;

  @Override
  public Optional<User> getUserByPrincipalAndIsActive(String principal,
                                                      boolean isActive) {
    return userRepository.findByPrincipalAndIsActive(principal, isActive);
  }

  @Override
  public User save(User user) {
    return userRepository.save(user);
  }

  @Override
  public Optional<User> getUserByPrincipal(String principal) {
    return userRepository.findByPrincipal(principal);
  }

  @Override
  public Set<User> getUsersByPrincipalAndIsActive(Set<String> principalSet,
                                                  boolean isActive) {
    return userRepository.findAllByPrincipalAndIsActive(principalSet, isActive);
  }

  @Override
  public Set<User> saveAll(Set<User> users) {
    return new HashSet<>(userRepository.saveAll(users));
  }

  @Override
  public Set<User> getUsers(boolean isActive, boolean admin) {
    return userRepository.findAllByIsActive(isActive, admin);
  }
}
