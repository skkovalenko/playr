package org.skkov.playr.impl.mapper;

import org.mapstruct.Mapper;
import org.skkov.playr.api.dto.ParticipantDto;
import org.skkov.playr.api.dto.UserEventDto;
import org.skkov.playr.api.dto.UserProfileDto;
import org.skkov.playr.domain.tables.records.UserEventRecord;
import org.skkov.playr.domain.tables.records.UserProfileRecord;

/**
 * Описание UserProfileMapper.
 *
 * @author SKKOV
 */
@Mapper(componentModel = "spring")
public interface UserEventMapper {
  UserEventDto toDto(UserEventRecord entity);

  ParticipantDto toParticipantDto(UserEventRecord userEventRecord);
}
