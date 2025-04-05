package org.skkov.playr.api.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO для представления участия пользователя в мероприятии.
 */
@Data
public class UserEventDto {
  private UUID eventId;
  private String eventName;
  private String status;
  private LocalDateTime registeredAt;
}
