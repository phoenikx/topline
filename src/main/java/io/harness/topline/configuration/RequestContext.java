package io.harness.topline.configuration;

import org.slf4j.MDC;

public class RequestContext {
  public static final String REQUEST_ID_KEY_NAME = "requestId";

  private RequestContext() {}

  public static String getRequestIdKeyName() {
    return MDC.get(REQUEST_ID_KEY_NAME);
  }

  public static boolean removeRequestId() {
    MDC.remove(REQUEST_ID_KEY_NAME);
    return true;
  }

  public static String setRequestId(String requestId) {
    MDC.put(REQUEST_ID_KEY_NAME, requestId);
    return requestId;
  }

  public static String getRequestId() { return MDC.get(REQUEST_ID_KEY_NAME); }
}
