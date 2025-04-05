package org.skkov.playr.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для запроса на вход в систему.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
  /**
   * Email пользователя
   */
  private String email;
  /**
   * Пароль пользователя
   */
  private String password;
}