package io.harness.topline.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("referral-templates")
public class ReferralTemplate {
    private String data;
}
