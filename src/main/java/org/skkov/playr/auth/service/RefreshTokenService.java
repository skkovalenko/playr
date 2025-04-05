package org.skkov.playr.auth.service;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.skkov.playr.auth.repository.RefreshTokenRepository;
import org.skkov.playr.domain.tables.records.AccountRecord;
import org.skkov.playr.domain.tables.records.RefreshTokenRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
  private final RefreshTokenRepository refreshTokenRepository;

  public RefreshTokenRecord storeRefreshToken(UUID accountId, String token) {
    var refreshToken = new RefreshTokenRecord();
    refreshToken.setId(UUID.randomUUID());
    refreshToken.setAccountId(accountId);
    refreshToken.setToken(token); // Можно заменить на более безопасный генератор
    refreshToken.setExpiresAt(LocalDateTime.now().plusDays(7)); // Токен живёт 7 дней
    refreshToken.setRevoked(false);

    return refreshTokenRepository.save(refreshToken);
  }

  public RefreshTokenRecord verifyToken(String token) {
    return refreshTokenRepository.findByToken(token)
        .filter(it -> !it.getRevoked() && it.getExpiresAt().isAfter(LocalDateTime.now()))
        .orElseThrow(() -> new RuntimeException("Invalid or expired refresh token"));
  }

  @Transactional
  public void revokeTokens(AccountRecord user) {
    refreshTokenRepository.deleteByUserId(user.getId());
  }

  public void revokeToken(String refreshToken) {
    refreshTokenRepository.deleteByToken(refreshToken);
  }
}
