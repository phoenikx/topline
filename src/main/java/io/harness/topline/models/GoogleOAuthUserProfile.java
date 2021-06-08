package io.harness.topline.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleOAuthUserProfile {
    private String id;
    private String email;
    private String name;
    private String locale;
    private String picture;
    @JsonProperty("verified_email")
    private boolean verifiedEmail;
}
