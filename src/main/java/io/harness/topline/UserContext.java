package io.harness.topline;

import io.harness.topline.models.User;
import io.harness.topline.repositories.UserRepository;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

public class UserContext {
  @Autowired private static UserRepository userRepository;

  public static User getUser() {
    String email = MDC.get("email");
    return userRepository.getByEmail(email).orElse(
        User.builder().email(email).admin(false).build());
  }

  public static String getEmail() {
      return MDC.get("email");
  }
}
