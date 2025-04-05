package org.skkov.playr.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для запроса на сброс пароля.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {
  /**
   * Email пользователя
   */
  private String email;
}