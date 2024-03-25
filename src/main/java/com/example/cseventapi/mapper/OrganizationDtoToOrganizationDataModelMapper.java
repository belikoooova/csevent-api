package com.example.cseventapi.mapper;

import com.example.cseventapi.entity.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrganizationDtoToOrganizationDataModelMapper {
    Organization map(com.example.cseventapi.dto.Organization organization);
}
