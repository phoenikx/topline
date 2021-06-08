package io.harness.topline.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.harness.topline.configuration.auth.GoogleOAuthConfig;
import io.harness.topline.configuration.auth.LinkedInOAuthConfig;
import io.harness.topline.enums.OAuthProvider;
import io.harness.topline.exceptions.InvalidRequestException;
import io.harness.topline.models.GoogleOAuthUserProfile;
import io.harness.topline.services.api.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OAuthServiceImpl implements OAuthService {
  private final GoogleOAuthConfig googleOAuthConfig;
  private final LinkedInOAuthConfig linkedInOAuthConfig;
  private final ObjectMapper objectMapper;
  private final OAuthClient oAuthClient;

  @Override
  public GoogleOAuthUserProfile
  fetchProfileFromOAuthProvider(@NotNull String authCode,
                                @NotNull OAuthProvider oAuthProvider) {
    switch (oAuthProvider) {
    case GOOGLE:
      try {
        OAuthClientRequest request =
            OAuthClientRequest.tokenLocation(googleOAuthConfig.getTokenUrl())
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setClientId(googleOAuthConfig.getClientId())
                .setClientSecret(googleOAuthConfig.getClientSecret())
                .setRedirectURI(googleOAuthConfig.getRedirectUrl())
                .setCode(authCode)
                .buildQueryMessage();
        OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(
            request, OAuthJSONAccessTokenResponse.class);
        String accessToken = oAuthResponse.getAccessToken();

        OAuthClientRequest bearerClientRequest =
            new OAuthBearerClientRequest(googleOAuthConfig.getProfileUrl())
                .setAccessToken(accessToken)
                .buildQueryMessage();

        OAuthResourceResponse resourceResponse =
            oAuthClient.resource(bearerClientRequest, OAuth.HttpMethod.GET,
                                 OAuthResourceResponse.class);
        return objectMapper.readValue(resourceResponse.getBody(),
                                      GoogleOAuthUserProfile.class);
      } catch (Exception ex) {
        log.error("Error while fetching profile from {}, error: {}",
                  oAuthProvider, ex);
        throw new InvalidRequestException("Unable to fetch profile from: " +
                                          oAuthProvider);
      }
    case LINKEDIN:
      try {
        OAuthClientRequest oAuthClientRequest = OAuthClientRequest
                .tokenLocation(linkedInOAuthConfig.getTokenUrl())
                .setClientId(linkedInOAuthConfig.getClientId())
                .setClientSecret(linkedInOAuthConfig.getClientSecret())
                .setRedirectURI(linkedInOAuthConfig.getRedirectUrl())
                .setCode(authCode)
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .buildQueryMessage();
        OAuthJSONAccessTokenResponse oAuthJSONAccessTokenResponse = oAuthClient.accessToken(oAuthClientRequest, OAuthJSONAccessTokenResponse.class);
        String accessToken = oAuthJSONAccessTokenResponse.getAccessToken();
      } catch (Exception ex) {
        log.error("Error while fetching profile from {}, error: {}",
                oAuthProvider, ex);
        throw new InvalidRequestException("Unable to fetch profile from: " +
                oAuthProvider);
      }
    default:
      throw new InvalidRequestException("This oauth provider " + oAuthProvider +
                                        " is not supported yet.");
    }
  }
}
