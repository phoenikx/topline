package io.harness.topline.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("referral-templates")
public class ReferralTemplate {
    @Id private String id;
    private String data;
}
