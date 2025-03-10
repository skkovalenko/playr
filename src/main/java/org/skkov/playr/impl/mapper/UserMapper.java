package org.skkov.playr.impl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.skkov.playr.api.dto.UserDto;
import org.skkov.playr.api.dto.auth.RegisterRequest;
import org.skkov.playr.domain.tables.records.UserProfileRecord;

/**
 * Преобразователь для работы с пользователем.
 *
 * @author SKKOV
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
  /**
   * Преобразование.
   *
   * @param user профиль пользователя
   * @return Дто пользователя
   */
  UserDto toDto(UserProfileRecord user);

  /**
   * Преобразование.
   *
   * @param dto Дто пользователя
   * @return профиль пользователя
   */
  UserProfileRecord toEntity(UserDto dto);

  /**
   * Преобразование.
   *
   * @param request запрос на регистрацию
   * @return профиль пользователя
   */
  UserProfileRecord toEntity(RegisterRequest request);
}
