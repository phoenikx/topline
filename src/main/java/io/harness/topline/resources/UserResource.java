package io.harness.topline.resources;

import io.harness.topline.exceptions.InvalidRequestException;
import io.harness.topline.models.User;
import io.harness.topline.services.api.UserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserResource {
  @Autowired private UserService userService;

  @GetMapping("/admin")
  public List<User> getAdmins() {
    return new ArrayList<>(userService.getUsers(true, true));
  }

  @GetMapping("/me")
  public User getLoggedInUser() {
    return userService.getLoggedInUser().orElseThrow(()-> new InvalidRequestException("No such user exists"));
  }
}
