package org.skkov.playr.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для ответа с JWT-токеном.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
  /**
   * JWT-токен
   */
  private String accessToken;
  /**
   * Refresh-токен
   */
  private String refreshToken;
}