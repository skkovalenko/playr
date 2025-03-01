package org.skkov.playr.domain.repositoty;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.skkov.playr.domain.model.UserProfile;
import org.springframework.stereotype.Repository;

/**
 * Описание UserRepository.
 *
 * @author SKKOV
 */
@Repository
@RequiredArgsConstructor
public class UserRepository {
  private final DSLContext context;
  public Optional<UserProfile> findById(UUID id) {

    return Optional.ofNullable(null);
  }

  public UserProfile save(UserProfile user) {
    return null;
  }
}
