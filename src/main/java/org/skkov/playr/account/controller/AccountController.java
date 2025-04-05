package org.skkov.playr.account.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.skkov.playr.account.dto.AccountDto;
import org.skkov.playr.account.dto.ChangePasswordRequest;
import org.skkov.playr.account.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST-контроллер для управления аккаунтами пользователей.
 * <p>
 * Позволяет пользователю управлять собственными учетными данными: просматривать информацию об
 * аккаунте, изменять пароль и удалять аккаунт.
 * </p>
 *
 * @author SKKOV
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
@Tag(name = "Аккаунты", description = "API для работы с аккаунтами")
public class AccountController {
  private final AccountService accountService;

  /**
   * Получение информации о текущем аккаунте пользователя.
   *
   * @param authentication текущий авторизованный пользователь.
   * @return данные текущего аккаунта пользователя.
   */
  @GetMapping("/me")
  public ResponseEntity<AccountDto> getCurrentAccount(Authentication authentication) {
    return ResponseEntity.ok(accountService.getCurrentAccount(authentication));
  }

  /**
   * Изменение пароля текущего пользователя.
   *
   * @param authentication текущий авторизованный пользователь.
   * @param request        объект с текущим и новым паролем.
   * @return HTTP-статус 200 при успешной смене пароля.
   */
  @PutMapping("/me/password")
  public ResponseEntity<?> changePassword(
      Authentication authentication,
      @RequestBody ChangePasswordRequest request
  ) {
    accountService.changePassword(authentication, request);
    return ResponseEntity.ok().build();
  }

  /**
   * Удаление аккаунта текущего пользователя вместе с профилем и связанными данными.
   *
   * @param authentication текущий авторизованный пользователь.
   * @return HTTP-статус 204 No Content при успешном удалении.
   */
  @DeleteMapping("/me")
  public ResponseEntity<?> deleteAccount(Authentication authentication) {
    accountService.deleteAccount(authentication);
    return ResponseEntity.noContent().build();
  }
}
