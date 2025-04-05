package org.skkov.playr.account.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

/**
 * DTO пользователя для API.
 */
@Data
public class AccountDto {
  private UUID id;
  private String email;
  private String role;
  private String status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
