package org.skkov.playr.event.mapper;

import org.mapstruct.Mapper;
import org.skkov.playr.event.dto.EventDto;
import org.skkov.playr.domain.tables.records.EventRecord;

/**
 * Описание EventMapper.
 *
 * @author SKKOV
 */
@Mapper(componentModel = "spring")
public interface EventMapper {
  /**
   * Преобразование.
   *
   * @param dto Дто события
   * @return Сущность события
   */
  EventRecord toEntity(EventDto dto);

  /**
   * Преобразование.
   *
   * @param entity Сущность события
   * @return Дто события
   */
  EventDto toDto(EventRecord entity);
}
