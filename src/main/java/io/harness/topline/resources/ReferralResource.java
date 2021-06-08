package io.harness.topline.resources;

import io.harness.topline.UserContext;
import io.harness.topline.models.CustomerReferral;
import io.harness.topline.services.api.ReferralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer-referrals")
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
