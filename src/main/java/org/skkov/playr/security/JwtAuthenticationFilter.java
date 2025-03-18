package org.skkov.playr.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Фильтр для обработки JWT-аутентификации.
 *
 * @author SKKOV
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtProvider jwtProvider;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws ServletException, IOException {
    String token = extractToken(request);

    if (token != null && jwtProvider.validateToken(token)) {
      String username = jwtProvider.getUsernameFromToken(token);
      List<GrantedAuthority> authorities = jwtProvider.getAuthoritiesFromToken(token); // Получаем роли из токена

      var authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }

  private String extractToken(HttpServletRequest request) {
    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    return (authHeader != null && authHeader.startsWith("Bearer ")) ? authHeader.substring(7)
        : null;
  }
}
