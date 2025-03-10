package org.skkov.playr.api.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для OAuth-авторизации.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuthRequest {
  /**
   * Провайдер (Google, VK, Telegram)
   */
  private String provider;
  /**
   * Токен, полученный от OAuth-провайдера
   */
  private String token;
}