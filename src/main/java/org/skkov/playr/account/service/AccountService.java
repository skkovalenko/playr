package org.skkov.playr.account.service;

import lombok.RequiredArgsConstructor;
import org.skkov.playr.account.dto.AccountDto;
import org.skkov.playr.account.dto.ChangePasswordRequest;
import org.skkov.playr.account.repository.AccountRepository;
import org.skkov.playr.account.mapper.AccountMapper;
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
