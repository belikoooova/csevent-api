package com.example.cseventapi.mapper;

import com.example.cseventapi.dto.Cocktail;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CocktailDataModelToCocktailDtoMapper {
    Cocktail map(com.example.cseventapi.entity.Cocktail cocktailDataModel);
}
