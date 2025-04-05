package org.skkov.playr.account.repository;

import static org.skkov.playr.domain.tables.UserProfile.USER_PROFILE;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.skkov.playr.domain.tables.records.UserProfileRecord;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с таблицей user_profile на основе JOOQ.
 */
@Repository
@RequiredArgsConstructor
public class UserProfileRepository {
  private final DSLContext context;

  /**
   * Найти профиль по ID.
   *
   * @param id UUID профиля
   * @return Optional с профилем
   */
  public Optional<UserProfileRecord> findById(UUID id) {
    return context.selectFrom(USER_PROFILE)
        .where(USER_PROFILE.ID.eq(id))
        .fetchOptional(USER_PROFILE::from);
  }

  /**
   * Найти профиль по account_id.
   *
   * @param accountId UUID аккаунта
   * @return Optional с профилем
   */
  public Optional<UserProfileRecord> findByAccountId(UUID accountId) {
    return context
        .selectFrom(USER_PROFILE)
        .where(USER_PROFILE.ACCOUNT_ID.eq(accountId))
        .fetchOptional(USER_PROFILE::from);
  }

  /**
   * Сохранить или обновить профиль.
   *
   * @param profile объект профиля
   * @return обновлённый/сохранённый профиль
   */
  public UserProfileRecord save(UserProfileRecord profile) {
    UserProfileRecord record = context.newRecord(USER_PROFILE, profile);
    record.store();
    return record.into(USER_PROFILE);
  }

  /**
   * Поиск профилей по имени, фамилии или username.
   *
   * @param query поисковая строка
   * @return список подходящих профилей
   */
  public List<UserProfileRecord> search(String query) {
    String like = "%" + query.toLowerCase() + "%";
    return context
        .select(USER_PROFILE.fields())
        .from(USER_PROFILE)
        .where(
            DSL.or(
                USER_PROFILE.USERNAME.likeIgnoreCase(like),
                USER_PROFILE.FIRST_NAME.likeIgnoreCase(like),
                USER_PROFILE.LAST_NAME.likeIgnoreCase(like)
            )
        )
        .fetch(USER_PROFILE::from);
  }

  /**
   * Получить все профили.
   *
   * @return список всех профилей
   */
  public List<UserProfileRecord> findAll() {
    return context
        .selectFrom(USER_PROFILE)
        .fetch(USER_PROFILE::from);
  }
}
