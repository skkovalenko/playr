package org.skkov.playr.account.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

/**
 * Описание UserProfileDto.
 *
 * @author SKKOV
 */
@Data
public class UserProfileDto {
  private UUID id;
  private String username;
  private String firstName;
  private String lastName;
  private String avatarUrl;
  private String bio;
  private LocalDate birthDate;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
