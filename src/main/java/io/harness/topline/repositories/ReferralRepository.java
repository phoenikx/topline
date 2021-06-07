package io.harness.topline.repositories;

import io.harness.topline.models.CustomerReferral;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferralRepository extends PagingAndSortingRepository<CustomerReferral, String> {
    List<CustomerReferral> findCustomerReferralsByReferrer(String referrer, Sort sort);
}
