package com.setronica.eventing.mapper;

import com.setronica.eventing.dto.TicketOrderDto;
import com.setronica.eventing.persistence.TicketOrder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class TicketOrderMapper {

    public abstract TicketOrderDto mapToDto(TicketOrder entity);

    public abstract TicketOrder mapToTicketOrder(TicketOrderDto dto);
}
