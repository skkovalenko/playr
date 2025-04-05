package org.skkov.playr.participation.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

/**
 * DTO для отображения участника мероприятия.
 *
 * @author WorkSKKov
 */
@Data
public class ParticipantDto {
  /**
   * ID аккаунта участника
   */
  private UUID accountId;
  /**
   * ID профиля (UserProfile), если есть
   */
  private UUID profileId;
  /**
   * Username участника
   */
  private String username;
  /**
   * Имя участника
   */
  private String firstName;
  /**
   * Фамилия участника
   */
  private String lastName;
  /**
   * Ссылка на аватар участника
   */
  private String avatarUrl;
  /**
   * Статус участия: REGISTERED, APPROVED, CANCELLED и т.п.
   */
  private String status;
  /**
   * Время подачи заявки/регистрации
   */
  private LocalDateTime registeredAt;
}
