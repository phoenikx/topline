package io.harness.topline.services.api;


public interface BearerTokenService {
    String createToken(String subject);

    String getSubject(String token);

    void blackListToken(String token);

    boolean isBlackListedToken(String token);
}
