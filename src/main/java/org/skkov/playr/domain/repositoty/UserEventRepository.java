package org.skkov.playr.domain.repositoty;

import static org.skkov.playr.domain.tables.UserEvent.USER_EVENT;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.skkov.playr.domain.tables.records.UserEventRecord;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с таблицей user_event через JOOQ.
 */
@Repository
@RequiredArgsConstructor
public class UserEventRepository {

  private final DSLContext context;

  /**
   * Найти все участия пользователя по accountId, с опциональным фильтром по статусу.
   *
   * @param accountId ID пользователя
   * @param status    статус участия (null — без фильтра)
   * @return список записей участия
   */
  public List<UserEventRecord> findByAccountId(UUID accountId, String status) {
    var query = context
        .selectFrom(USER_EVENT)
        .where(USER_EVENT.ACCOUNT_ID.eq(accountId));
    if (status != null) {
      query = query
          .and(USER_EVENT.STATUS.eq(status));
    }
    return query
        .fetch(USER_EVENT::from);
  }

  /**
   * Найти всех участников конкретного мероприятия.
   *
   * @param eventId ID мероприятия
   * @return список записей участия
   */
  public List<UserEventRecord> findByEventId(UUID eventId) {
    return context
        .selectFrom(USER_EVENT)
        .where(USER_EVENT.EVENT_ID.eq(eventId))
        .fetch(USER_EVENT::from);
  }

  /**
   * Проверить, участвует ли пользователь в мероприятии.
   *
   * @param accountId ID пользователя
   * @param eventId   ID мероприятия
   * @return Optional с записью, если найдена
   */
  public Optional<UserEventRecord> findByAccountAndEvent(UUID accountId, UUID eventId) {
    return context
        .selectFrom(USER_EVENT)
        .where(
            DSL.and(
                USER_EVENT.ACCOUNT_ID.eq(accountId),
                USER_EVENT.EVENT_ID.eq(eventId)
            )
        )
        .fetchOptional(USER_EVENT::from);
  }

  /**
   * Сохранить или обновить участие.
   *
   * @param entity объект участия
   * @return сохранённый объект
   */
  public UserEventRecord save(UserEventRecord entity) {
    UserEventRecord record = context
        .newRecord(USER_EVENT, entity);
    record.store(); // insert/update
    return record.into(USER_EVENT);
  }

  /**
   * Удалить участие.
   *
   * @param accountId ID пользователя
   * @param eventId   ID мероприятия
   */
  public void delete(UUID accountId, UUID eventId) {
    context
        .deleteFrom(USER_EVENT)
        .where(
            DSL.and(
                USER_EVENT.ACCOUNT_ID.eq(accountId),
                USER_EVENT.EVENT_ID.eq(eventId))
        )
        .execute();
  }
}
