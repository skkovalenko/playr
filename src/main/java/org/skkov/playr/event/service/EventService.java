package org.skkov.playr.event.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.skkov.playr.event.dto.EventDto;
import org.skkov.playr.event.repository.EventRepository;
import org.skkov.playr.event.mapper.EventMapper;
import org.springframework.stereotype.Service;

/**
 * Сервис для управления мероприятиями.
 */
@Service
@RequiredArgsConstructor
public class EventService {
  private final EventRepository repository;
  private final EventMapper eventMapper;

  /**
   * Создает новое мероприятие.
   *
   * @param eventDto данные нового мероприятия
   * @return созданное мероприятие
   */
  public EventDto createEvent(EventDto eventDto) {
    eventDto.setId(UUID.randomUUID());
    repository.save(eventMapper.toEntity(eventDto));
    return eventDto;
  }

  /**
   * Получает список всех мероприятий.
   *
   * @return список мероприятий
   */
  public List<EventDto> getAllEvents() {
    return repository
        .findAll()
        .stream()
        .map(eventMapper::toDto)
        .collect(Collectors.toList());
  }

  /**
   * Получает мероприятие по его идентификатору.
   *
   * @param id идентификатор мероприятия
   * @return найденное мероприятие
   * @throws NoSuchElementException если мероприятие не найдено
   */
  public EventDto getEventById(UUID id) {
    return repository
        .findById(id)
        .map(eventMapper::toDto)
        .orElseThrow(() -> new NoSuchElementException("Event not found"));
  }

  /**
   * Обновляет существующее мероприятие.
   *
   * @param id       идентификатор мероприятия
   * @param eventDto новые данные мероприятия
   * @return обновленное мероприятие
   * @throws NoSuchElementException если мероприятие не найдено
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
   * Удаляет мероприятие по его идентификатору.
   *
   * @param id идентификатор мероприятия
   */
  public void deleteEvent(UUID id) {
    repository.deleteById(id);
  }

  /**
   * Подать заявку на участие в мероприятии.
   *
   * @param id идентификатор мероприятия
   */
  public void joinEvent(UUID id) {
    if (repository.findById(id).isEmpty()) {
      throw new NoSuchElementException("Event not found");
    }
    // Логика добавления пользователя в список заявок на участие
  }

  /**
   * Одобрить участника мероприятия.
   *
   * @param id     идентификатор мероприятия
   * @param userId идентификатор пользователя
   */
  public void approveParticipant(UUID id, UUID userId) {
    if (repository.findById(id).isEmpty()) {
      throw new NoSuchElementException("Event not found");
    }
    // Логика подтверждения участника
  }
}
