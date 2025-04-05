package org.skkov.playr.participation.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.skkov.playr.participation.dto.ParticipantDto;
import org.skkov.playr.participation.dto.UserEventDto;
import org.skkov.playr.event.repository.EventRepository;
import org.skkov.playr.participation.repository.UserEventRepository;
import org.skkov.playr.domain.tables.records.EventRecord;
import org.skkov.playr.domain.tables.records.UserEventRecord;
import org.skkov.playr.participation.mapper.UserEventMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Сервис для получения мероприятий пользователя и участников событий.
 */
@Service
@RequiredArgsConstructor
public class UserEventService {
  private final UserEventRepository userEventRepository;
  private final EventRepository eventRepository;
  private final UserEventMapper userEventMapper;

  /**
   * Получить список мероприятий, в которых участвует текущий пользователь.
   *
   * @param authentication текущий пользователь
   * @param status         статус участия (необязательно)
   * @return список мероприятий пользователя
   */
  public List<UserEventDto> getMyEvents(Authentication authentication, String status) {
    UUID accountId = UUID.fromString(authentication.getName());
    List<UserEventRecord> events = userEventRepository.findByAccountId(accountId, status);
    return events
        .stream()
        .map(userEventMapper::toDto)
        .collect(Collectors.toList());
  }

  /**
   * Получить участников мероприятия по ID, если пользователь — организатор.
   *
   * @param authentication текущий пользователь
   * @param eventId        идентификатор мероприятия
   * @return список участников
   */
  public List<ParticipantDto> getEventParticipants(Authentication authentication, UUID eventId) {
    UUID accountId = UUID.fromString(authentication.getName());
    EventRecord event = eventRepository
        .findById(eventId)
        .orElseThrow(() -> new RuntimeException("Мероприятие не найдено"));

    if (!event.getOrganizerAccountId().equals(accountId)) {
      throw new SecurityException("Доступ разрешён только организатору мероприятия.");
    }

    return userEventRepository
        .findByEventId(eventId)
        .stream()
        .map(userEventMapper::toParticipantDto)
        .collect(Collectors.toList());
  }
}
