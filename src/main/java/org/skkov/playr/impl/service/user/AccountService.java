package org.skkov.playr.impl.service.user;

import lombok.RequiredArgsConstructor;
import org.skkov.playr.api.dto.account.AccountDto;
import org.skkov.playr.api.dto.account.ChangePasswordRequest;
import org.skkov.playr.domain.repositoty.AccountRepository;
import org.skkov.playr.impl.mapper.AccountMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Сервис для управления пользователями.
 */
@Service
@RequiredArgsConstructor
public class AccountService {
  private final AccountRepository accountRepository;
  private final AccountMapper accountMapper;

  public AccountDto getCurrentAccount(Authentication authentication) {
    return null;
  }

  public void changePassword(Authentication authentication, ChangePasswordRequest request) {
  }

  public void deleteAccount(Authentication authentication) {

  }
}
