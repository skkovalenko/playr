package org.skkov.playr.domain.repositoty;

import static org.skkov.playr.domain.tables.UserProfile.USER_PROFILE;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.skkov.playr.domain.tables.records.UserProfileRecord;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с профилем пользователя.
 *
 * @author SKKOV
 */
@Repository
@RequiredArgsConstructor
public class UserRepository {
  private final DSLContext context;

  /**
   * Возвращает профиль пользователя.
   *
   * @param id идентификатор пользователя
   * @return контейнер с найденным профилем пользователя
   */
  public Optional<UserProfileRecord> findById(UUID id) {
    return context
        .select(USER_PROFILE.fields())
        .from(USER_PROFILE)
        .where(USER_PROFILE.ID.eq(id))
        .fetchOptional(USER_PROFILE::from);
  }

  /**
   * Сохранение пользователя профиль.
   *
   * @param user сущность
   * @return сохраненная сущность
   */
  public UserProfileRecord save(UserProfileRecord user) {
    context
        .insertInto(USER_PROFILE)
        .set(USER_PROFILE.ID, user.getId())
        .set(USER_PROFILE.EMAIL, user.getEmail())
        .set(USER_PROFILE.USERNAME, user.getUsername())
        .set(USER_PROFILE.PASSWORD, user.getPassword())
        .set(USER_PROFILE.FIRST_NAME, user.getFirstName())
        .set(USER_PROFILE.LAST_NAME, user.getLastName())
        .set(USER_PROFILE.CREATED_AT, DSL.defaultValue(LocalDateTime.class))
        .set(USER_PROFILE.UPDATED_AT, DSL.defaultValue(LocalDateTime.class))
        .execute();
    return user;
  }

  public boolean existsByEmail(String email) {
    return context
        .fetchExists(
            context
                .selectFrom(USER_PROFILE)
                .where(USER_PROFILE.EMAIL.eq(email))
        );
  }

  public Optional<UserProfileRecord> findByEmail(String email) {
    return context
        .select(USER_PROFILE.fields())
        .from(USER_PROFILE)
        .where(USER_PROFILE.EMAIL.eq(email))
        .fetchOptional(USER_PROFILE::from);
  }
}
