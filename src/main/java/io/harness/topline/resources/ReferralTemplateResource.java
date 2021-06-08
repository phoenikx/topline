package io.harness.topline.resources;

import io.harness.topline.exceptions.InvalidRequestException;
import io.harness.topline.models.ReferralTemplate;
import io.harness.topline.services.api.ReferralTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("referral-templates")
public class ReferralTemplateResource {
    private final ReferralTemplateService referralTemplateService;

    public ReferralTemplateResource(ReferralTemplateService referralTemplateService) {
        this.referralTemplateService = referralTemplateService;
    }

    @PostMapping
    ReferralTemplate create(ReferralTemplate referralTemplate) {
        return referralTemplateService.create(referralTemplate.getData());
    }

    @GetMapping("{id}")
    ReferralTemplate get(@PathVariable("id") String id) {
        return referralTemplateService.get(id).orElseThrow(()-> new InvalidRequestException("No such template found"));
    }

    @GetMapping
    List<ReferralTemplate> list() {
        return referralTemplateService.list();
    }
}
