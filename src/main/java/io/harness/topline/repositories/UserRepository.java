package io.harness.topline.repositories;

import io.harness.topline.models.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, String> {
    Optional<User> getByEmail(String email);
}
