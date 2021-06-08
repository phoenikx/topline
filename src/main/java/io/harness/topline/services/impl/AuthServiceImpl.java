package io.harness.topline.services.impl;

import com.google.common.collect.Sets;
import io.harness.topline.configuration.BearerTokenRealm;
import io.harness.topline.configuration.auth.GoogleOAuthConfig;
import io.harness.topline.configuration.auth.LinkedInOAuthConfig;
import io.harness.topline.enums.OAuthProvider;
import io.harness.topline.exceptions.InvalidRequestException;
import io.harness.topline.models.GoogleOAuthUserProfile;
import io.harness.topline.models.User;
import io.harness.topline.services.api.AuthService;
import io.harness.topline.services.api.BearerTokenService;
import io.harness.topline.services.api.OAuthService;
import io.harness.topline.services.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthServiceImpl implements AuthService {
    private Set<String> allowedEmailDomains = Sets.newHashSet("@harness.io");
    private final UserService userService;
    private final BearerTokenService bearerTokenService;
    private final OAuthService oAuthService;
    private final GoogleOAuthConfig googleOAuthConfig;
    private final LinkedInOAuthConfig linkedInOAuthConfig;
    private final BearerTokenRealm bearerTokenRealm;

    @Override
    public Optional<String> login(@NotNull String authCode,
                                  @NotNull OAuthProvider oAuthProvider) {
        GoogleOAuthUserProfile userProfile =
                oAuthService.fetchProfileFromOAuthProvider(authCode, oAuthProvider);
        String email = userProfile.getEmail();
        String name = userProfile.getName();
        Optional<User> userOptional = userService.getUserByPrincipal(email);
        if (!userOptional.isPresent()) {
            checkValidityAndCreateDefaultUser(email, name, userProfile);
        }
        String token = bearerTokenService.createToken(email);
        return Optional.of(token);
    }

    private User checkValidityAndCreateDefaultUser(
            @NotNull String email, @NotNull String name,
            @NotNull GoogleOAuthUserProfile userProfile) {
        if (!userProfile.isVerifiedEmail()) {
            throw new InvalidRequestException(
                    "This email is not verified by Oauth Provider."
                            + " Access is allowed only for verified profiles.");
        } else if (!isValidEmail(email)) {
            throw new InvalidRequestException(
                    "This email account is not allowed access to the portal,"
                            + " please contact administrator.");
        }
        return userService.createUser(email, email, name);
    }

    @Override
    public boolean logout(@NotNull String token) {
        bearerTokenService.blackListToken(token);
        return true;
    }

    @Override
    public Optional<String> getConfig(@NotNull OAuthProvider provider) {
        switch (provider) {
            case GOOGLE:
                try {
                    return Optional.ofNullable(
                            OAuthClientRequest.authorizationProvider(OAuthProviderType.GOOGLE)
                                    .setClientId(googleOAuthConfig.getClientId())
                                    .setScope(googleOAuthConfig.getScope())
                                    .setResponseType(googleOAuthConfig.getResponseType())
                                    .setRedirectURI(googleOAuthConfig.getRedirectUrl())
                                    .buildQueryMessage()
                                    .getLocationUri());
                } catch (Exception ex) {
                    log.error("Error while generating url for :" + provider);
                    return Optional.empty();
                }
            case LINKEDIN:
                try {
                    return Optional.ofNullable(OAuthClientRequest.authorizationProvider(OAuthProviderType.LINKEDIN)
                            .setClientId(linkedInOAuthConfig.getClientId())
                            .setScope(linkedInOAuthConfig.getScope())
                            .setResponseType(linkedInOAuthConfig.getResponseType())
                            .setRedirectURI(linkedInOAuthConfig.getRedirectUrl())
                            .buildQueryMessage().getLocationUri());
                } catch (Exception ex) {
                    log.error("Error while generating url for :" + provider);
                    return Optional.empty();
                }

            case GITHUB:
            default:
                return Optional.empty();
        }
    }

    @Override
    public void clearCachedAuthorizingInfo(String principal) {
        bearerTokenRealm.clearCachedAuthorizingInfo(principal);
    }

    private boolean isValidEmail(@NotNull String email) {
        for (String suffix : allowedEmailDomains) {
            if (email.endsWith(suffix)) {
                return true;
            }
        }
        return false;
    }
}
