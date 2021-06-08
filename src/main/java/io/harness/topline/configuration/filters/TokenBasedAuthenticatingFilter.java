package io.harness.topline.configuration.filters;

import io.harness.topline.models.BearerToken;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

@Slf4j
@Getter
@Setter
public class TokenBasedAuthenticatingFilter extends AuthenticatingFilter {
  private static final Pattern AUTHORIZATION_HEADER_VALUE_FORMAT =
      Pattern.compile("^Bearer ([^\\s]+)$");
  private static final String AUTHORIZATION_HEADER_KEY = "Authorization";

  @Override
  protected boolean onAccessDenied(ServletRequest request,
                                   ServletResponse response) throws Exception {
    boolean authenticated = executeLogin(request, response);

    // Return 401 if authentication failed
    if (!authenticated)
      WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED,
                                          "Invalid credentials");
    return authenticated;
  }

  @Override
  protected final boolean isLoginRequest(ServletRequest request,
                                         ServletResponse response) {
    String authzHeaderValue = getAuthzHeader(request);
    return !(StringUtils.isEmpty(authzHeaderValue)) &&
        AUTHORIZATION_HEADER_VALUE_FORMAT.matcher(authzHeaderValue).matches();
  }

  private String getAuthzHeader(ServletRequest request) {
    HttpServletRequest httpRequest = WebUtils.toHttp(request);
    return httpRequest.getHeader(AUTHORIZATION_HEADER_KEY);
  }

  @Override
  protected AuthenticationToken createToken(ServletRequest request,
                                            ServletResponse response) {
    String authorizationHeader = getAuthzHeader(request);
    if (authorizationHeader == null)
      return BearerToken.builder().token("").build();

    Matcher matcher =
        AUTHORIZATION_HEADER_VALUE_FORMAT.matcher(authorizationHeader);
    if (matcher.matches())
      return BearerToken.builder().token(matcher.group(1)).build();
    return BearerToken.builder().token("").build();
  }
}