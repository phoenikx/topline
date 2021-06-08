package io.harness.topline.configuration.filters;

import io.harness.topline.configuration.RequestContext;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class CorrelationFilter extends HandlerInterceptorAdapter {
  private static final String CORRELATION_ID_HEADER_NAME = "X-request-id";

  @Override
  public boolean preHandle(final HttpServletRequest request,
                           final HttpServletResponse response,
                           final Object handler) {
    String correlationId = getCorrelationIdFromHeader(request);
    if (StringUtils.isEmpty(correlationId)) {
      correlationId = generateUniqueRequestId();
    }
    RequestContext.setRequestId(correlationId);
    return true;
  }

  @Override
  public void afterCompletion(final HttpServletRequest request,
                              final HttpServletResponse response,
                              final Object handler, final Exception ex) {
    RequestContext.removeRequestId();
  }

  private String getCorrelationIdFromHeader(final HttpServletRequest request) {
    return request.getHeader(CORRELATION_ID_HEADER_NAME);
  }

  private String generateUniqueRequestId() {
    return UUID.randomUUID().toString();
  }
}