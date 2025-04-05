package org.skkov.playr.auth.repository;

import static org.skkov.playr.domain.Tables.REFRESH_TOKEN;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.skkov.playr.domain.tables.RefreshToken;
import org.skkov.playr.domain.tables.records.RefreshTokenRecord;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с токеном.
 */
@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {
  private final DSLContext context;

  /**
   * Поиск сущности токена по значению токена.
   *
   * @param token токен
   * @return контейнер с сущностью
   */
  public Optional<RefreshTokenRecord> findByToken(String token) {
    return context
        .select(REFRESH_TOKEN.fields())
        .from(REFRESH_TOKEN)
        .where(REFRESH_TOKEN.TOKEN.eq(token))
        .fetchOptional(REFRESH_TOKEN::from);
  }

  public RefreshTokenRecord save(RefreshTokenRecord refreshToken) {
    context
        .insertInto(RefreshToken.REFRESH_TOKEN)
        .set(REFRESH_TOKEN.ID, refreshToken.getId())
        .set(REFRESH_TOKEN.ACCOUNT_ID, refreshToken.getAccountId())
        .set(REFRESH_TOKEN.TOKEN, refreshToken.getToken())
        .set(REFRESH_TOKEN.EXPIRES_AT, refreshToken.getExpiresAt())
        .set(REFRESH_TOKEN.REVOKED, refreshToken.getRevoked())
        .execute();

    return refreshToken;
  }

  public void deleteByUserId(UUID id) {
    context
        .deleteFrom(RefreshToken.REFRESH_TOKEN)
        .where(REFRESH_TOKEN.ACCOUNT_ID.eq(id))
        .execute();
  }

  public void deleteByToken(String refreshToken) {
    context
        .deleteFrom(REFRESH_TOKEN)
        .where(REFRESH_TOKEN.TOKEN.eq(refreshToken))
        .execute();
  }
}
