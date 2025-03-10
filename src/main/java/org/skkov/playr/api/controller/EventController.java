package org.skkov.playr.api.controller;

import java.util.List;
import java.util.UUID;
import org.skkov.playr.api.dto.EventDto;
import org.skkov.playr.impl.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для управления мероприятиями.
 */
@RestController
@RequestMapping("/events")
public class EventController {
  private final EventService eventService;

  /**
   * Конструктор контроллера мероприятий.
   *
   * @param eventService Сервис для работы с мероприятиями.
   */
  public EventController(EventService eventService) {
    this.eventService = eventService;
  }

  /**
   * Создание нового мероприятия.
   *
   * @param eventDto Данные мероприятия.
   * @return Созданное мероприятие.
   */
  @PostMapping
  public ResponseEntity<EventDto> createEvent(@RequestBody EventDto eventDto) {
    return ResponseEntity.ok(eventService.createEvent(eventDto));
  }

  /**
   * Получение списка всех мероприятий.
   *
   * @return Список мероприятий.
   */
  @GetMapping
  public ResponseEntity<List<EventDto>> getEvents() {
    return ResponseEntity.ok(eventService.getAllEvents());
  }

  /**
   * Получение информации о конкретном мероприятии.
   *
   * @param id Идентификатор мероприятия.
   * @return Информация о мероприятии.
   */
  @GetMapping("/{id}")
  public ResponseEntity<EventDto> getEventById(@PathVariable UUID id) {
    return ResponseEntity.ok(eventService.getEventById(id));
  }

  /**
   * Обновление данных мероприятия.
   *
   * @param id       Идентификатор мероприятия.
   * @param eventDto Новые данные мероприятия.
   * @return Обновленное мероприятие.
   */
  @PutMapping("/{id}")
  public ResponseEntity<EventDto> updateEvent(
      @PathVariable UUID id,
      @RequestBody EventDto eventDto
  ) {
    return ResponseEntity.ok(eventService.updateEvent(id, eventDto));
  }

  /**
   * Удаление мероприятия.
   *
   * @param id Идентификатор мероприятия.
   * @return Ответ без содержимого.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEvent(@PathVariable UUID id) {
    eventService.deleteEvent(id);
    return ResponseEntity.noContent().build();
  }

  /**
   * Подать заявку на участие в мероприятии.
   *
   * @param id Идентификатор мероприятия.
   * @return Подтверждение отправки заявки.
   */
  @PostMapping("/{id}/join")
  public ResponseEntity<String> joinEvent(@PathVariable UUID id) {
    eventService.joinEvent(id);
    return ResponseEntity.ok("Request to join event sent.");
  }

  /**
   * Одобрить заявку участника.
   *
   * @param id     Идентификатор мероприятия.
   * @param userId Идентификатор пользователя.
   * @return Подтверждение одобрения участника.
   */
  @PostMapping("/{id}/approve")
  public ResponseEntity<String> approveParticipant(
      @PathVariable UUID id,
      @RequestParam UUID userId
  ) {
    eventService.approveParticipant(id, userId);
    return ResponseEntity.ok("Participant approved.");
  }
}
