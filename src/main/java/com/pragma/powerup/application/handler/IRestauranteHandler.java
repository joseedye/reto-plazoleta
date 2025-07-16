package com.pragma.powerup.application.handler;


import com.pragma.powerup.application.dto.request.RestauranteRequestDto;
import com.pragma.powerup.application.dto.response.RestauranteResponseDto;

import java.util.List;

public interface IRestauranteHandler {

    RestauranteResponseDto saveRestaurante(RestauranteRequestDto restauranteRequestDto);

    List<RestauranteResponseDto> listarRestaurantes(int pagina,int tamanio);

}
