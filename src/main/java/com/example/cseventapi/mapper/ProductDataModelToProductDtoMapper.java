package com.example.cseventapi.mapper;

import com.example.cseventapi.dto.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductDataModelToProductDtoMapper {
    Product map(com.example.cseventapi.entity.Product productDataModel);
}
