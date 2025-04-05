package org.skkov.playr.account.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.skkov.playr.domain.tables.Account;
import org.skkov.playr.domain.tables.records.AccountRecord;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с профилем пользователя.
 *
 * @author SKKOV
 */
@Repository
@RequiredArgsConstructor
public class AccountRepository {
  private final DSLContext context;

  /**
   * Возвращает профиль пользователя.
   *
   * @param id идентификатор пользователя
   * @return контейнер с найденным профилем пользователя
   */
  public Optional<AccountRecord> findById(UUID id) {
    return context
        .select(Account.ACCOUNT.fields())
        .from(Account.ACCOUNT)
        .where(Account.ACCOUNT.ID.eq(id))
        .fetchOptional(Account.ACCOUNT::from);
  }

  /**
   * Сохранение пользователя профиль.
   *
   * @param user сущность
   * @return сохраненная сущность
   */
  public AccountRecord save(AccountRecord user) {
    context
        .insertInto(Account.ACCOUNT)
        .set(Account.ACCOUNT.ID, user.getId())
        .set(Account.ACCOUNT.EMAIL, user.getEmail())
        .set(Account.ACCOUNT.PASSWORD, user.getPassword())
        .set(Account.ACCOUNT.CREATED_AT, DSL.defaultValue(LocalDateTime.class))
        .set(Account.ACCOUNT.UPDATED_AT, DSL.defaultValue(LocalDateTime.class))
        .execute();
    return user;
  }

  public boolean existsByEmail(String email) {
    return context
        .fetchExists(
            context
                .selectFrom(Account.ACCOUNT)
                .where(Account.ACCOUNT.EMAIL.eq(email))
        );
  }

  public Optional<AccountRecord> findByEmail(String email) {
    return context
        .select(Account.ACCOUNT.fields())
        .from(Account.ACCOUNT)
        .where(Account.ACCOUNT.EMAIL.eq(email))
        .fetchOptional(Account.ACCOUNT::from);
  }
}
