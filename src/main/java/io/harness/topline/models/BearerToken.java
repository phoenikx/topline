package io.harness.topline.models;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.shiro.authc.AuthenticationToken;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "blacklisted_tokens")
@Builder
public class BearerToken
    extends BaseModel<String> implements AuthenticationToken {
  @Id private String id;

  @Indexed private String token;

  @Override
  public Object getPrincipal() {
    return token;
  }

  @Override
  public Object getCredentials() {
    return token;
  }
}
