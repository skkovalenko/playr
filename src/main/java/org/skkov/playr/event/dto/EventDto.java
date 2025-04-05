package org.skkov.playr.event.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

/**
 * Data Transfer Object (DTO) для мероприятий.
 */
@Data
public class EventDto {
  /**
   * Уникальный идентификатор мероприятия
   */
  private UUID id;
  /**
   * Название мероприятия
   */
  private String name;
  /**
   * Описание мероприятия
   */
  private String description;
  /**
   * Дата и время начала мероприятия
   */
  private LocalDateTime startTime;
  /**
   * Дата и время окончания мероприятия
   */
  private LocalDateTime endTime;
  /**
   * Локация мероприятия
   */
  private String location;
  /**
   * Максимальное количество участников
   */
  private int maxParticipants;
  /**
   * ID организатора мероприятия
   */
  private UUID organizerUserProfileId;
}
