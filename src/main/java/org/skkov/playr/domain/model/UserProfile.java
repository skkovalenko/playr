package org.skkov.playr.domain.model;

import java.util.UUID;
import lombok.Data;

/**
 * Сущность пользователя.
 *
 * @author SKKOV
 */
@Data
public class UserProfile {
  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
  private String avatarUrl;
}
