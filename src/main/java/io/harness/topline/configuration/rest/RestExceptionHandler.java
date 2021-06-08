package io.harness.topline.configuration.rest;

import io.harness.topline.configuration.RequestContext;
import io.harness.topline.exceptions.InvalidRequestException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String ERROR_MSG = "Caught exception";

  private RestResponseBody getRestError(String exceptionStr,
                                        WebRequest webRequest) {
    return RestResponseBody.builder()
        .data(exceptionStr)
        .path(((ServletWebRequest)webRequest).getRequest().getRequestURI())
        .requestId(RequestContext.getRequestIdKeyName())
        .build();
  }

  // thrown when there is @Valid annotation on a method argument but values
  // supplied don't meet constraints
  @Override
  protected ResponseEntity<Object>
  handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                               HttpHeaders headers, HttpStatus status,
                               WebRequest request) {
    log.error(ERROR_MSG, ex);
    RestResponseBody errorDetails = getRestError(
        "Invalid request. Arguments not valid. Please try again.", request);
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {InvalidRequestException.class})
  protected ResponseEntity<RestResponseBody>
  handleConflict(InvalidRequestException ex, WebRequest request) {
    log.error(ERROR_MSG, ex);
    RestResponseBody errorDetails =
        getRestError(Optional.ofNullable(ex.getMessage())
                         .orElse("Invalid request, please try again"),
                     request);
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {AuthenticationException.class})
  protected ResponseEntity<RestResponseBody>
  handleConflict(AuthenticationException ex, WebRequest request) {
    log.error(ERROR_MSG, ex);
    RestResponseBody errorDetails =
        getRestError("Invalid credentials.", request);
    return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(value = {AuthorizationException.class})
  protected ResponseEntity<RestResponseBody>
  handleConflict(AuthorizationException ex, WebRequest request) {
    log.error(ERROR_MSG, ex);
    RestResponseBody errorDetails =
        getRestError("Insufficient permissions.", request);
    return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(value = {Exception.class})
  protected ResponseEntity<RestResponseBody>
  handleConflict(Exception ex, WebRequest request) {
    log.error(ERROR_MSG, ex);
    RestResponseBody errorDetails = getRestError("Internal error.", request);
    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}