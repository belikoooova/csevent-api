package com.example.cseventapi.mapper;

import com.example.cseventapi.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductDtoToProductDataModelMapper {
    Product map(com.example.cseventapi.dto.Product productDto);
}
