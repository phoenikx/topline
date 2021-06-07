package io.harness.topline.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Builder
@Document("referrals")
public class CustomerReferral {
    @Id private String id;
    @Indexed private String companyName;
    private String message;
    private Set<String> followUps;
    private Set<PointOfContact> pointOfContacts;
    @Indexed private String referrer;
    @Builder.Default ReferralStatus status=ReferralStatus.INITIATED;
}
