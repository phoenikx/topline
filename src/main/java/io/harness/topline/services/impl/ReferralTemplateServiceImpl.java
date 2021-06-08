package io.harness.topline.services.impl;

import io.harness.topline.models.ReferralTemplate;
import io.harness.topline.repositories.mongo.ReferralTemplateRepository;
import io.harness.topline.services.api.ReferralTemplateService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ReferralTemplateServiceImpl implements ReferralTemplateService {
  private final ReferralTemplateRepository referralTemplateRepository;

  public ReferralTemplateServiceImpl(
      ReferralTemplateRepository referralTemplateRepository) {
    this.referralTemplateRepository = referralTemplateRepository;
  }

  @Override
  public Optional<ReferralTemplate> get(String id) {
    return referralTemplateRepository.findById(id);
  }

  @Override
  public List<ReferralTemplate> list() {
    List<ReferralTemplate> referralTemplates = new ArrayList<>();
    referralTemplateRepository.findAll().forEach(referralTemplates::add);
    return referralTemplates;
  }

  @Override
  public ReferralTemplate create(String data) {
    return referralTemplateRepository.save(
        ReferralTemplate.builder().data(data).build());
  }
}
