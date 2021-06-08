package io.harness.topline.services.api;

import io.harness.topline.models.ReferralTemplate;

import java.util.List;
import java.util.Optional;

public interface ReferralTemplateService {
    Optional<ReferralTemplate> get(String id);

    List<ReferralTemplate> list();

    ReferralTemplate create(String data);
}
