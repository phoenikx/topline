package io.harness.topline.resources;

import io.harness.topline.enums.OAuthProvider;
import io.harness.topline.exceptions.InternalException;
import io.harness.topline.reqresps.requests.LoginRequest;
import io.harness.topline.reqresps.responses.LoginResponse;
import io.harness.topline.reqresps.responses.OAuthConfigResponse;
import io.harness.topline.services.api.AuthService;
import io.harness.topline.utils.AuthorizationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = "auth",
        consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AuthResource {
  @Autowired private AuthService authService;

  @PostMapping(path = "oauth/login")
  public LoginResponse loginUser(@Valid @RequestBody LoginRequest loginRequest,
                                 @RequestParam(value = "oauth-provider",
                                               defaultValue = "GOOGLE")
                                 OAuthProvider oAuthProvider) {
    Optional<String> tokenOptional =
        authService.login(loginRequest.getAuthCode(), oAuthProvider);
    if (!tokenOptional.isPresent())
      throw new InternalException("Failed to generate a token for authCode: " +
                                  loginRequest.getAuthCode());
    return LoginResponse.builder().token(tokenOptional.get()).build();
  }

  @GetMapping(path = "oauth/config")
  public OAuthConfigResponse getOauthConfig(
      @RequestParam(value = "oauth-provider",
                    defaultValue = "GOOGLE") OAuthProvider provider) {
    Optional<String> redirectUrlOptional = authService.getConfig(provider);
    if (!redirectUrlOptional.isPresent()) {
      throw new InternalException("Cannot fetch config for provider: " +
                                  provider);
    }
    return OAuthConfigResponse.builder()
        .redirectUrl(redirectUrlOptional.get())
        .build();
  }

  @PostMapping(path = "logout")
  public Boolean
  logoutUser(@RequestHeader("Authorization") String authorizationHeader) {
    Optional<String> authorizationToken =
        AuthorizationUtil.getAuthorizationTokenFromHeader(authorizationHeader);
    return authorizationToken.filter(token -> authService.logout(token))
        .isPresent();
  }
}
