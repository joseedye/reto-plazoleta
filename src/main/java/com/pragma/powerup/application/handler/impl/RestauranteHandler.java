package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.RestauranteRequestDto;
import com.pragma.powerup.application.dto.response.RestauranteResponseDto;
import com.pragma.powerup.application.handler.IRestauranteHandler;
import com.pragma.powerup.application.mapper.IRestauranteRequestMapper;
import com.pragma.powerup.application.mapper.IRestauranteResponseMapper;
import com.pragma.powerup.domain.api.IRestauranteServicePort;
import com.pragma.powerup.domain.api.IUsuarioServicePort;
import com.pragma.powerup.domain.model.Propietario;
import com.pragma.powerup.domain.model.Restaurante;
import com.pragma.powerup.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RestauranteHandler implements IRestauranteHandler {

    private final IRestauranteServicePort restauranteServicePort;
    private final IRestauranteResponseMapper restauranteResponseMapper;
    private final IRestauranteRequestMapper restauranteRequestMapper;
    private final IUsuarioServicePort usuarioServicePort;

    @Override
    public RestauranteResponseDto saveRestaurante(RestauranteRequestDto restauranteRequestDto) {
       Restaurante restaurante = restauranteRequestMapper.toRestaurante(restauranteRequestDto);
       Usuario propietario = usuarioServicePort.getUsuario(restauranteRequestDto.getPropietarioId());
       restaurante.setPropietario(propietario);

       return restauranteResponseMapper.toResponse(restauranteServicePort.saveRestaurante(restaurante));
  }

    @Override
    public List<RestauranteResponseDto> listarRestaurantes(int pagina,int tamanio) {
        return restauranteResponseMapper.toResponseList(restauranteServicePort.listarRestaurantes(pagina,tamanio));

    }
}
