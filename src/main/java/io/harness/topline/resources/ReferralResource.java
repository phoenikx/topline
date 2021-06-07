package io.harness.topline.resources;

import io.harness.topline.UserContext;
import io.harness.topline.models.CustomerReferral;
import io.harness.topline.services.ReferralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReferralResource {
    private final ReferralService referralService;

    @Autowired
    public ReferralResource(ReferralService referralService) {
        this.referralService = referralService;
    }

    @PostMapping
    public CustomerReferral createReferral(@RequestBody CustomerReferral referral) {
        return referralService.create(referral);
    }

    @GetMapping
    List<CustomerReferral> getReferrals() {
        return referralService.getReferrals(UserContext.getEmail());
    }
}
