package com.example.cseventapi.mapper;

import com.example.cseventapi.dto.Event;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventDataModelToEventDtoMapper {
    Event map(com.example.cseventapi.entity.Event eventDataModel);
}
