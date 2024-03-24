package com.example.cseventapi.mapper;

import com.example.cseventapi.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventDtoToEventDataModelMapper {
    Event map(com.example.cseventapi.dto.Event eventDto);
}
