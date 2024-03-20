package com.example.cseventapi.mapper;

import com.example.cseventapi.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserDtoToUserDataModelMapper {
    User map(com.example.cseventapi.dto.User userDto);
}
