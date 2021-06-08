package io.harness.topline.repositories.mongo;

import io.harness.topline.models.ReferralTemplate;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferralTemplateRepository
    extends PagingAndSortingRepository<ReferralTemplate, String> {}
