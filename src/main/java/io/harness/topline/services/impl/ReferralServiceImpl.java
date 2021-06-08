package io.harness.topline.services.impl;

import io.harness.topline.UserContext;
import io.harness.topline.exceptions.InvalidRequestException;
import io.harness.topline.models.CustomerReferral;
import io.harness.topline.models.ReferralStatus;
import io.harness.topline.models.User;
import io.harness.topline.repositories.mongo.ReferralRepository;
import io.harness.topline.repositories.mongo.UserRepository;
import io.harness.topline.services.api.ReferralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReferralServiceImpl implements ReferralService {
  private final ReferralRepository referralRepository;
  private final UserRepository userRepository;

  @Autowired
  public ReferralServiceImpl(ReferralRepository referralRepository,
                             UserRepository userRepository) {
    this.referralRepository = referralRepository;
    this.userRepository = userRepository;
  }

  @Override
  public CustomerReferral create(CustomerReferral customerReferral) {
    if (!customerReferral.getReferrer().equals(UserContext.getEmail())) {
      throw new InvalidRequestException("cannot created with a different user");
    }
    return referralRepository.save(customerReferral);
  }

  @Override
  public List<CustomerReferral> getReferrals(String email) {
    User user = userRepository.getByEmail(email).orElseThrow(
        () -> new InvalidRequestException("No such user found."));
    List<CustomerReferral> referrals = new ArrayList<>();
    if (user.isAdmin()) {
      referralRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
          .forEach(referrals::add);
    } else {
      referrals.addAll(referralRepository.findCustomerReferralsByReferrer(
          email, Sort.by(Sort.Direction.DESC, "createdAt")));
    }
    return referrals;
  }

  @Override
  public CustomerReferral updateStatus(String id,
                                       ReferralStatus referralStatus) {
    // TODO: check if user has access here
    CustomerReferral customerReferral =
        referralRepository.findById(id).orElseThrow(
            () -> new InvalidRequestException("No such referral found."));
    customerReferral.setStatus(referralStatus);
    return referralRepository.save(customerReferral);
  }
}
