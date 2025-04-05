package org.skkov.playr.participation.mapper;

import org.mapstruct.Mapper;
import org.skkov.playr.participation.dto.ParticipantDto;
import org.skkov.playr.participation.dto.UserEventDto;
import org.skkov.playr.domain.tables.records.UserEventRecord;

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
