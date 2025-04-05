package org.skkov.playr.participation.controller;

import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.skkov.playr.participation.dto.ParticipantDto;
import org.skkov.playr.participation.dto.UserEventDto;
import org.skkov.playr.participation.service.UserEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер для получения мероприятий, в которых участвует текущий пользователь,
 * а также получения списка участников мероприятия (для организатора).
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "User Event API", description = "Мероприятия пользователя и список участников")
public class UserEventController {
  private final UserEventService userEventService;

  /**
   * Получить список мероприятий, в которых участвует текущий пользователь.
   *
   * @param authentication текущий авторизованный пользователь
   * @param status фильтр по статусу участия (например: REGISTERED, APPROVED)
   * @return список мероприятий пользователя
   */
  @Operation(summary = "Список мероприятий текущего пользователя")
  @GetMapping("/accounts/me/events")
  public ResponseEntity<List<UserEventDto>> getMyEvents(
      Authentication authentication,
      @RequestParam(required = false) String status
  ) {
    return ResponseEntity.ok(userEventService.getMyEvents(authentication, status));
  }

  /**
   * Получить список участников конкретного мероприятия.
   * Доступно только организатору мероприятия.
   *
   * @param authentication текущий авторизованный пользователь
   * @param eventId ID мероприятия
   * @return список участников мероприятия
   */
  @Operation(summary = "Список участников мероприятия")
  @GetMapping("/events/{eventId}/participants")
  public ResponseEntity<List<ParticipantDto>> getEventParticipants(
      Authentication authentication,
      @PathVariable UUID eventId
  ) {
    return ResponseEntity.ok(userEventService.getEventParticipants(authentication, eventId));
  }
}
