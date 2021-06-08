package io.harness.topline.configuration.filters;

import io.harness.topline.configuration.rest.CORSConfig;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter implements Filter {
  private static final String ACCESS_CONTROL_ALLOW_ORIGIN_KEY =
      "Access-Control-Allow-Origin";
  private static final String ACCESS_CONTROL_MAX_AGE_KEY =
      "Access-Control-Max-Age";
  private static final String ACCESS_CONTROL_ALLOW_HEADERS_KEY =
      "Access-Control-Allow-Headers";
  private static final String ACCESS_CONTROL_ALLOW_METHODS_KEY =
      "Access-Control-Allow-Methods";
  @Autowired private CORSConfig corsConfig;

  @Override
  public void doFilter(ServletRequest req, ServletResponse res,
                       FilterChain chain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest)req;
    HttpServletResponse response = (HttpServletResponse)res;
    if (CorsUtils.isCorsRequest(request) &&
        request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
      response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN_KEY,
                         corsConfig.getAccessControlAllowOrigin());
      response.setHeader(ACCESS_CONTROL_ALLOW_METHODS_KEY,
                         corsConfig.getAccessControlAllowMethods());
      response.setHeader(ACCESS_CONTROL_MAX_AGE_KEY,
                         corsConfig.getAccessControlMaxAge());
      response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS_KEY,
                         corsConfig.getAccessControlAllowHeaders());
    }
    chain.doFilter(req, res);
  }

  @Override
  public void destroy() {}

  @Override
  public void init(FilterConfig config) {}
}
