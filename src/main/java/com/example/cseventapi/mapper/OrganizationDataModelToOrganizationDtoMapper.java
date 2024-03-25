package com.example.cseventapi.mapper;

import com.example.cseventapi.dto.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrganizationDataModelToOrganizationDtoMapper {
    Organization map(com.example.cseventapi.entity.Organization organizationDataModel);
}
