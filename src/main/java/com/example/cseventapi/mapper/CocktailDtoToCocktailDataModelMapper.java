package com.example.cseventapi.mapper;

import com.example.cseventapi.entity.Cocktail;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CocktailDtoToCocktailDataModelMapper {
    Cocktail map(com.example.cseventapi.dto.Cocktail cocktailDto);
}
