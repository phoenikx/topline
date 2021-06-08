package io.harness.topline.configuration;

import io.harness.topline.models.BearerToken;
import io.harness.topline.models.User;
import io.harness.topline.services.api.BearerTokenService;
import io.harness.topline.services.api.UserService;
import java.util.Optional;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class BearerTokenRealm extends AuthorizingRealm {
  private static final String REALM_NAME = "bearer_token_realm";
  @Autowired private BearerTokenService bearerTokenService;
  @Autowired private UserService userService;

  public void clearCachedAuthorizingInfo(String principal) {
    super.clearCachedAuthorizationInfo(
        new SimplePrincipalCollection(principal, REALM_NAME));
  }

  @Override
  public boolean supports(AuthenticationToken authenticationToken) {
    return authenticationToken instanceof BearerToken;
  }

  @Override
  protected AuthorizationInfo
  doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    String principal = (String)principalCollection.getPrimaryPrincipal();
    Optional<User> userOptional = userService.getUserByPrincipal(principal);
    if (!userOptional.isPresent())
      return null;
    return new SimpleAuthorizationInfo();
  }

  @Override
  protected AuthenticationInfo
  doGetAuthenticationInfo(AuthenticationToken authenticationToken)
      throws AuthenticationException {
    BearerToken bearerToken = (BearerToken)authenticationToken;
    String token = bearerToken.getToken();
    String subject = bearerTokenService.getSubject(token);
    return new SimpleAuthenticationInfo(subject, token, REALM_NAME);
  }
}
