package org.skkov.playr.impl.mapper;

import org.mapstruct.Mapper;
import org.skkov.playr.api.dto.UserProfileDto;
import org.skkov.playr.domain.tables.records.UserProfileRecord;

/**
 * Описание UserProfileMapper.
 *
 * @author SKKOV
 */
@Mapper(componentModel = "spring")
public interface UserProfileMapper {
  UserProfileDto toDto(UserProfileRecord profile);
}
