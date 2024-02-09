package com.setronica.eventing.mapper;

import com.setronica.eventing.dto.EventDto;
import com.setronica.eventing.persistence.Event;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.lang.reflect.Array;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class EventMapper {

    public abstract EventDto mapToDto(Event entity);

    public abstract Event mapToEvent(EventDto dto);

    public abstract List<EventDto> mapToDtoList(List<Event> entities);

    public abstract List<Event> mapToEventList(List<EventDto> dtos);

}
