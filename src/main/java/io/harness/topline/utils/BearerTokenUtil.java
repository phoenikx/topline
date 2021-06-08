package io.harness.topline.utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class BearerTokenUtil {
  public String createToken(@NotNull String secretKey, @NotNull Date issuedAt,
                            @NotNull Date expiresAt, @NotNull String issuer,
                            SignatureAlgorithm signatureAlgorithm) {
    JwtBuilder builder = Jwts.builder()
                             .setId(UUID.randomUUID().toString())
                             .setIssuedAt(issuedAt)
                             .setExpiration(expiresAt)
                             .setIssuer(issuer)
                             .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()),
                                       signatureAlgorithm);
    return builder.compact();
  }
}
