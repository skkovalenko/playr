package org.skkov.playr.impl.service.auth;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.skkov.playr.api.dto.auth.AuthResponse;
import org.skkov.playr.api.dto.auth.LoginRequest;
import org.skkov.playr.api.dto.auth.RefreshTokenRequest;
import org.skkov.playr.api.dto.auth.RegisterRequest;
import org.skkov.playr.api.dto.auth.ResetPasswordRequest;
import org.skkov.playr.domain.repositoty.UserRepository;
import org.skkov.playr.domain.tables.records.UserProfileRecord;
import org.skkov.playr.impl.mapper.UserMapper;
import org.skkov.playr.security.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для обработки аутентификации и авторизации пользователей.
 */
@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;
  private final JwtProvider jwtProvider;
  private final RefreshTokenService refreshTokenService;

  /**
   * Регистрация нового пользователя.
   */
  public AuthResponse register(RegisterRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new RuntimeException("Email is already in use");
    }
    var user = userMapper.toEntity(request);
    user.setId(UUID.randomUUID());
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    userRepository.save(user);

    String token = jwtProvider.generateAccessToken(user.getEmail());
    return new AuthResponse(token, jwtProvider.generateRefreshToken(user.getEmail()));
  }

  /**
   * Аутентификация пользователя.
   */
  public AuthResponse login(LoginRequest request) {
    UserProfileRecord user = userRepository
        .findByEmail(request.getEmail())
        .orElseThrow(() -> new RuntimeException("User not found"));

    String token = jwtProvider.generateAccessToken(request.getEmail());
    String refreshToken = jwtProvider.generateRefreshToken(request.getEmail());
    refreshTokenService.storeRefreshToken(user.getId(), refreshToken);

    return new AuthResponse(token, refreshToken);
  }

  /**
   * Обновление JWT-токена.
   */
  public AuthResponse refreshToken(RefreshTokenRequest request) {
    String refreshToken = request.getRefreshToken();

    if (!jwtProvider.validateToken(refreshToken)) {
      throw new RuntimeException("Invalid or expired refresh token");
    }

    String email = jwtProvider.getEmailFromToken(refreshToken);
    var user = userRepository
        .findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));

    // Отзываем старый refresh-токен
    refreshTokenService.revokeTokens(user);

    // Генерируем новый
    String newAccessToken = jwtProvider.generateAccessToken(user.getEmail());
    String newRefreshToken = jwtProvider.generateRefreshToken(user.getEmail());

    refreshTokenService.storeRefreshToken(user.getId(), newRefreshToken);

    return new AuthResponse(newAccessToken, newRefreshToken);
  }

  /**
   * Восстановление пароля.
   */
  public void resetPassword(ResetPasswordRequest request) {
    var userOptional = userRepository.findByEmail(request.getEmail());
    if (userOptional.isPresent()) {
      var user = userOptional.get();
      String newPassword = "newRandomPassword"; // Нужно заменить на генератор пароля
      user.setPassword(passwordEncoder.encode(newPassword));
      userRepository.save(user);
      // Отправить новый пароль пользователю на email
    }
  }

  @Transactional
  public void logout(RefreshTokenRequest request) {
    refreshTokenService.revokeToken(request.getRefreshToken());
  }
}
