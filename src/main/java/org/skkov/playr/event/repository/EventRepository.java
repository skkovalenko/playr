package org.skkov.playr.event.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.skkov.playr.domain.tables.records.EventRecord;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.skkov.playr.domain.Tables.EVENT;

/**
 * Репозиторий для работы с мероприятиями с использованием JOOQ.
 */
@Repository
@RequiredArgsConstructor
public class EventRepository {

  private final DSLContext context;

  /**
   * Создаёт новое мероприятие в базе данных.
   *
   * @param event DTO мероприятия
   * @return Созданное мероприятие
   */
  public EventRecord save(EventRecord event) {
    context.insertInto(EVENT)
        .set(EVENT.ID, event.getId())
        .set(EVENT.NAME, event.getName())
        .set(EVENT.DESCRIPTION, event.getDescription())
        .set(EVENT.START_TIME, event.getStartTime())
        .set(EVENT.END_TIME, event.getEndTime())
        .set(EVENT.LOCATION, event.getLocation())
        .set(EVENT.MAX_PARTICIPANTS, event.getMaxParticipants())
        .set(EVENT.ORGANIZER_ACCOUNT_ID, event.getOrganizerAccountId())
        .execute();

    return event;
  }

  /**
   * Получает мероприятие по ID.
   *
   * @param id ID мероприятия
   * @return Найденное мероприятие, если оно существует
   */
  public Optional<EventRecord> findById(UUID id) {
    Record record = context.selectFrom(EVENT)
        .where(EVENT.ID.eq(id))
        .fetchOne();

    return Optional.ofNullable(record).map(EVENT::from);
  }

  /**
   * Получает список всех мероприятий.
   *
   * @return Список мероприятий
   */
  public List<EventRecord> findAll() {
    return context
        .selectFrom(EVENT)
        .fetch()
        .map(EVENT::from);
  }

  /**
   * Обновляет мероприятие в базе данных.
   *
   * @param event сущность мероприятия
   * @return Обновлённое мероприятие
   */
  public EventRecord update(EventRecord event) {
    context.update(EVENT)
        .set(EVENT.NAME, event.getName())
        .set(EVENT.DESCRIPTION, event.getDescription())
        .set(EVENT.START_TIME, event.getStartTime())
        .set(EVENT.END_TIME, event.getEndTime())
        .set(EVENT.LOCATION, event.getLocation())
        .set(EVENT.MAX_PARTICIPANTS, event.getMaxParticipants())
        .where(EVENT.ID.eq(event.getId()))
        .execute();

    return event;
  }

  /**
   * Удаляет мероприятие по ID.
   *
   * @param id ID мероприятия
   */
  public void deleteById(UUID id) {
    context.deleteFrom(EVENT)
        .where(EVENT.ID.eq(id))
        .execute();
  }
}
