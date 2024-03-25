package com.example.cseventapi.mapper;

import com.example.cseventapi.dto.Warehouse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WarehouseDataModelToWarehouseDtoMapper {
    Warehouse map(com.example.cseventapi.entity.Warehouse warehouseDataModel);
}
