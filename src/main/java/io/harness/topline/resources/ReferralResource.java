package io.harness.topline.resources;

import io.harness.topline.UserContext;
import io.harness.topline.models.CustomerReferral;
import io.harness.topline.models.ReferralStatus;
import io.harness.topline.services.api.ReferralService;
import io.harness.topline.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer-referrals")
public class ReferralResource {
    private final ReferralService referralService;
    private final UserService userService;

    @Autowired
    public ReferralResource(ReferralService referralService, UserService userService) {
        this.referralService = referralService;
        this.userService = userService;
    }

    @PostMapping
    public CustomerReferral createReferral(@RequestBody CustomerReferral referral) {
        return referralService.create(referral);
    }

    @GetMapping
    List<CustomerReferral> getReferrals() {
        return referralService.getReferrals(UserContext.getEmail());
    }

    @PutMapping("{id}")
    CustomerReferral updateStatus(@PathVariable("id") String id, ReferralStatus referralStatus) {
        return referralService.updateStatus(id, referralStatus);
    }
}
