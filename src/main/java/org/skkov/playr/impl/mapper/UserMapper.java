package org.skkov.playr.impl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.skkov.playr.api.dto.UserDTO;
import org.skkov.playr.domain.model.UserProfile;

/**
 * Описание UserMapper.
 *
 * @author SKKOV
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  UserDTO toDTO(UserProfile user);
  UserProfile toEntity(UserDTO dto);

}
