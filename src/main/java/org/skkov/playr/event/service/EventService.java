package org.skkov.playr.event.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.skkov.playr.common.enums.ParticipationStatus;
import org.skkov.playr.domain.tables.records.EventRecord;
import org.skkov.playr.domain.tables.records.UserEventRecord;
import org.skkov.playr.event.dto.EventDto;
import org.skkov.playr.event.mapper.EventMapper;
import org.skkov.playr.event.repository.EventRepository;
import org.skkov.playr.participation.repository.UserEventRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Сервис для управления мероприятиями.
 */
@Service
@RequiredArgsConstructor
public class EventService {
  private final EventRepository repository;
  private final EventMapper eventMapper;
  private final UserEventRepository userEventRepository;

  /**
   * Создает новое мероприятие.
   */
  public EventDto createEvent(EventDto eventDto) {
    eventDto.setId(UUID.randomUUID());
    repository.save(eventMapper.toEntity(eventDto));
    return eventDto;
  }

  /**
   * Получает список всех мероприятий.
   */
  public List<EventDto> getAllEvents() {
    return repository
        .findAll()
        .stream()
        .map(eventMapper::toDto)
        .collect(Collectors.toList());
  }

  /**
   * Получает мероприятие по ID.
   */
  public EventDto getEventById(UUID id) {
    return repository.findById(id)
        .map(eventMapper::toDto)
        .orElseThrow(() -> new NoSuchElementException("Event not found"));
  }

  /**
   * Обновляет мероприятие.
   */
  public EventDto updateEvent(UUID id, EventDto eventDto) {
    if (repository.findById(id).isEmpty()) {
      throw new NoSuchElementException("Event not found");
    }
    eventDto.setId(id);
    repository.save(eventMapper.toEntity(eventDto));
    return eventDto;
  }

  /**
   * Удаляет мероприятие.
   */
  public void deleteEvent(UUID id) {
    repository.deleteById(id);
  }

  /**
   * Подать заявку на участие в мероприятии.
   */
  public void joinEvent(UUID eventId, Authentication authentication) {
    UUID accountId = UUID.fromString(authentication.getName());
    repository
        .findById(eventId)
        .orElseThrow(() -> new NoSuchElementException("Event not found"));

    var existing = userEventRepository.findByAccountAndEvent(accountId, eventId);
    if (existing.isPresent()) {
      throw new IllegalStateException("You have already joined this event");
    }

    var participant = new UserEventRecord();
    participant.setId(UUID.randomUUID());
    participant.setAccountId(accountId); // заглушка или загрузи Account
    participant.setEventId(eventId);
    participant.setStatus(ParticipationStatus.REGISTERED.name());
    participant.setRegisteredAt(LocalDateTime.now());
    userEventRepository.save(participant);
  }

  /**
   * Одобрить участие пользователя в мероприятии.
   */
  public void approveParticipant(UUID eventId, UUID userId, Authentication authentication) {
    UUID organizerId = UUID.fromString(authentication.getName());
    EventRecord event = repository
        .findById(eventId)
        .orElseThrow(() -> new NoSuchElementException("Event not found"));

    if (!event.getOrganizerAccountId().equals(organizerId)) {
      throw new SecurityException("Only the organizer can approve participants.");
    }

    UserEventRecord participant = userEventRepository
        .findByAccountAndEvent(userId, eventId)
        .orElseThrow(() -> new NoSuchElementException("User is not registered for this event"));

    participant.setStatus(ParticipationStatus.APPROVED.name());
    participant.setUpdatedAt(LocalDateTime.now());
    userEventRepository.save(participant);
  }

  /**
   * Отклонить участие.
   */
  public void rejectParticipant(UUID eventId, UUID userId, Authentication authentication) {
    UUID organizerId = UUID.fromString(authentication.getName());
    var event = repository
        .findById(eventId)
        .orElseThrow(() -> new NoSuchElementException("Event not found"));

    if (!event.getOrganizerAccountId().equals(organizerId)) {
      throw new SecurityException("Only the organizer can reject participants.");
    }

    var participant = userEventRepository
        .findByAccountAndEvent(userId, eventId)
        .orElseThrow(() -> new NoSuchElementException("User not registered"));

    participant.setStatus(ParticipationStatus.REJECTED.name());
    participant.setUpdatedAt(LocalDateTime.now());
    userEventRepository.save(participant);
  }

  /**
   * Отменить своё участие.
   */
  public void cancelParticipation(UUID eventId, Authentication authentication) {
    UUID accountId = UUID.fromString(authentication.getName());
    userEventRepository
        .findByAccountAndEvent(accountId, eventId)
        .orElseThrow(() -> new NoSuchElementException("Вы не зарегистрированы на мероприятие"));

    userEventRepository.delete(accountId, eventId);
  }
}
