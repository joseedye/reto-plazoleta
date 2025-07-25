package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.RestaurantePaginadoRequestDto;
import com.pragma.powerup.application.dto.request.RestauranteRequestDto;
import com.pragma.powerup.domain.model.Restaurante;
import com.pragma.powerup.domain.model.RestaurantePaginado;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)


public interface IRestauranteRequestMapper {

    Restaurante toRestaurante(RestauranteRequestDto restauranteRequestDto);

    RestaurantePaginado toRestaurantePaginado(RestaurantePaginadoRequestDto restaurantePaginadoRequestDto);

}
