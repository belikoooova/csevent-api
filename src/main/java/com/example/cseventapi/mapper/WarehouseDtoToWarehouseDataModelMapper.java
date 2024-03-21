package com.example.cseventapi.mapper;

import com.example.cseventapi.entity.Warehouse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WarehouseDtoToWarehouseDataModelMapper {
    Warehouse map(com.example.cseventapi.dto.Warehouse warehouseDto);
}
