package com.setronica.eventing.mapper;

import com.setronica.eventing.dto.EventScheduleDto;
import com.setronica.eventing.persistence.EventSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class EventScheduleMapper {

    public abstract EventScheduleDto mapToDto(EventSchedule entity);

    public abstract EventSchedule mapToEventSchedule(EventScheduleDto dto);
}
