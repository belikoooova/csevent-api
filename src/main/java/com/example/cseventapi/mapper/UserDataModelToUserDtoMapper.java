package com.example.cseventapi.mapper;

import com.example.cseventapi.dto.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserDataModelToUserDtoMapper {
    User map(com.example.cseventapi.entity.User userDataModel);
}
