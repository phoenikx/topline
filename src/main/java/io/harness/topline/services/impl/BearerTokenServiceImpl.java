package io.harness.topline.services.impl;

import io.harness.topline.daos.api.BearerTokenDao;
import io.harness.topline.models.BearerToken;
import io.harness.topline.services.api.BearerTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BearerTokenServiceImpl implements BearerTokenService {
  private long ttl = TimeUnit.DAYS.toMillis(1);
  @Value("${jwt.issuer:harness}") private String issuer;
  private BearerTokenDao bearerTokenDao;
  private Key signingKey;
  private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

  @Autowired
  public BearerTokenServiceImpl(@Autowired BearerTokenDao bearerTokenDao,
                                @Value("${jwt.secret}") String secretKey,
                                @Value("${jwt.issuer:harness}") String issuer) {
    log.info("ttl: {}", ttl);
    this.bearerTokenDao = bearerTokenDao;
    signingKey = Keys.hmacShaKeyFor(secretKey.getBytes());
    this.issuer = issuer;
  }

  public String createToken(@NotNull String subject) {
    long currentTimeMillis = System.currentTimeMillis();
    Date currentDate = new Date(currentTimeMillis);

    JwtBuilder builder = Jwts.builder()
                             .setId(UUID.randomUUID().toString())
                             .setIssuedAt(currentDate)
                             .setSubject(subject)
                             .setIssuer(issuer)
                             .setExpiration(new Date(currentTimeMillis + ttl))
                             .signWith(signingKey, signatureAlgorithm);

    return builder.compact();
  }

  public String getSubject(@NotNull String token) {
    Claims claims;
    try {
      claims = Jwts.parser()
                   .setSigningKey(signingKey)
                   .parseClaimsJws(token)
                   .getBody();
    } catch (Exception ex) {
      throw new AuthenticationException(
          "Invalid token."); // signature not verified
    }

    if (!claims.getIssuer().equals(issuer)) // issuer is not harness
      throw new AuthenticationException("Issuer of the token is not " + issuer);

    boolean blackListedToken = isBlackListedToken(token);

    if (blackListedToken) { // token has been blacklisted
      throw new AuthenticationException(
          "Use of a blacklisted token, please use a valid token");
    }

    return claims.getSubject();
  }

  @Override
  public void blackListToken(@NotNull String token) {
    BearerToken bearerToken = BearerToken.builder().token(token).build();
    bearerTokenDao.saveToken(bearerToken);
  }

  @Override
  public boolean isBlackListedToken(@NotNull String token) {
    Optional<BearerToken> bearerTokenOptional =
        bearerTokenDao.getBearerToken(token);
    return bearerTokenOptional.isPresent();
  }
}
