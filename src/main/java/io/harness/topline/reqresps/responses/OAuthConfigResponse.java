package io.harness.topline.reqresps.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OAuthConfigResponse {
  private String redirectUrl;
}
