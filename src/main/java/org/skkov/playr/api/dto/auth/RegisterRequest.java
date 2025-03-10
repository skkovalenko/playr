package org.skkov.playr.api.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для регистрации пользователя.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
  /**
   * Имя пользователя
   */
  private String firstName;
  /**
   * Фамилия пользователя
   */
  private String lastName;
  /**
   * Email пользователя
   */
  private String email;
  /**
   * Username
   */
  private String username;
  /**
   * Пароль пользователя
   */
  private String password;
}