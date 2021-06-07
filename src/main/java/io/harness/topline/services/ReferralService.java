package io.harness.topline.services;

import io.harness.topline.models.CustomerReferral;

import java.util.List;

public interface ReferralService {
  CustomerReferral create(CustomerReferral customerReferral);

  List<CustomerReferral> getReferrals(String email);
}
