package org.skkov.playr.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * .
 *
 * @author SKKOV
 */
@Slf4j
@Component
public class JwtProvider {
  private final SecretKey jwtSecret;
  private final long accessTokenValidity;
  private final long refreshTokenValidity;

  public JwtProvider(
      @Value("${jwt.secret}") String secret,
      @Value("${jwt.accessToken.expiration}") long accessTokenValidity,
      @Value("${jwt.refreshToken.expiration}") long refreshTokenValidity
  ) {
    this.jwtSecret = Keys.hmacShaKeyFor(secret.getBytes());
    this.accessTokenValidity = accessTokenValidity;
    this.refreshTokenValidity = refreshTokenValidity;
  }

  /**
   * Генерирует access token для пользователя
   *
   * @param email Email пользователя
   * @return JWT access token
   */
  public String generateAccessToken(String email) {
    return Jwts.builder()
        .subject(email)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + accessTokenValidity))
        .signWith(jwtSecret, SIG.HS256)
        .compact();
  }

  /**
   * Генерирует refresh token для пользователя
   *
   * @param email Email пользователя
   * @return JWT refresh token
   */
  public String generateRefreshToken(String email) {
    return Jwts.builder()
        .subject(email)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + refreshTokenValidity))
        .signWith(jwtSecret, SIG.HS256)
        .compact();
  }

  /**
   * Проверяет, является ли токен валидным
   *
   * @param token JWT токен
   * @return true, если токен валиден
   */
  public boolean validateToken(String token) {
    try {
      Jwts
          .parser()
          .verifyWith(jwtSecret)
          .build()
          .parseSignedClaims(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    }
    return false;
  }

  /**
   * Извлекает email (subject) из токена
   *
   * @param token JWT токен
   * @return email пользователя
   */
  public String getEmailFromToken(String token) {
    return Jwts
        .parser()
        .verifyWith(jwtSecret)
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();
  }

  public String getUsernameFromToken(String token) {
    return getClaims(token).getSubject();
  }

  public List<GrantedAuthority> getAuthoritiesFromToken(String token) {
    List<String> roles = getClaims(token).get("roles", List.class);
    if (roles == null || roles.isEmpty()) {
      return List.of();
    }
    return roles
        .stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  private Claims getClaims(String token) {
    return Jwts.parser()
        .verifyWith(jwtSecret)
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }
}
