package org.skkov.playr.api.controller;

import lombok.RequiredArgsConstructor;
import org.skkov.playr.api.dto.auth.AuthResponse;
import org.skkov.playr.api.dto.auth.LoginRequest;
import org.skkov.playr.api.dto.auth.OAuthRequest;
import org.skkov.playr.api.dto.auth.RefreshTokenRequest;
import org.skkov.playr.api.dto.auth.RegisterRequest;
import org.skkov.playr.api.dto.auth.ResetPasswordRequest;
import org.skkov.playr.impl.service.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для авторизации и аутентификации пользователей.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  /**
   * Регистрация нового пользователя.
   *
   * @param request данные для регистрации
   * @return зарегистрированный пользователь
   */
  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
    return ResponseEntity.ok(authService.register(request));
  }

  /**
   * Вход пользователя в систему.
   *
   * @param request данные для входа
   * @return токен аутентификации
   */
  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
    return ResponseEntity.ok(authService.login(request));
  }

  /**
   * Авторизация через OAuth (Google, VK, Telegram).
   *
   * @param request данные OAuth
   * @return токен аутентификации
   */
  @PostMapping("/oauth")
  public ResponseEntity<AuthResponse> oauth(@RequestBody OAuthRequest request) {
    //return ResponseEntity.ok(authService.oauthLogin(request));
    return ResponseEntity.notFound().build();
  }

  /**
   * Обновление JWT-токена.
   *
   * @param request данные для обновления токена
   * @return новый токен
   */
  @PostMapping("/refresh")
  public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest request) {
    return ResponseEntity.ok(authService.refreshToken(request));
  }

  /**
   * Восстановление пароля.
   *
   * @param request данные для восстановления пароля
   * @return сообщение о статусе операции
   */
  @PostMapping("/reset-password")
  public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
    authService.resetPassword(request);
    return ResponseEntity.ok("Password reset instructions sent.");
  }

  /**
   * Выход.
   *
   * @param request данные для выхода
   * @return сообщение о статусе операции
   */
  @PostMapping("/logout")
  public ResponseEntity<String> resetPassword(@RequestBody RefreshTokenRequest request) {
    authService.logout(request);
    return ResponseEntity.ok("Log out successful.");
  }
}
