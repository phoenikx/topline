package io.harness.topline.services.api;

import io.harness.topline.models.CustomerReferral;
import io.harness.topline.models.ReferralStatus;
import java.util.List;

public interface ReferralService {
  CustomerReferral create(CustomerReferral customerReferral);

  List<CustomerReferral> getReferrals(String email);

  CustomerReferral updateStatus(String id, ReferralStatus referralStatus);
}
