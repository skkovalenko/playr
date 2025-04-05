package org.skkov.playr.impl.mapper;

import org.mapstruct.Mapper;
import org.skkov.playr.api.dto.account.AccountDto;
import org.skkov.playr.api.dto.auth.RegisterRequest;
import org.skkov.playr.domain.tables.records.AccountRecord;

/**
 * Преобразователь для работы с пользователем.
 *
 * @author SKKOV
 */
@Mapper(componentModel = "spring")
public interface AccountMapper {
  /**
   * Преобразование.
   *
   * @param user профиль пользователя
   * @return Дто пользователя
   */
  AccountDto toDto(AccountRecord user);

  /**
   * Преобразование.
   *
   * @param dto Дто пользователя
   * @return профиль пользователя
   */
  AccountRecord toEntity(AccountDto dto);

  /**
   * Преобразование.
   *
   * @param request запрос на регистрацию
   * @return профиль пользователя
   */
  AccountRecord toEntity(RegisterRequest request);
}
