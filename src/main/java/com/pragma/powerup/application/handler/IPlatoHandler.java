package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.PlatoActualizaRequestDto;
import com.pragma.powerup.application.dto.request.PlatoPaginadoRequestDto;
import com.pragma.powerup.application.dto.request.PlatoRequestDto;
import com.pragma.powerup.application.dto.response.GenericoPaginadoResponseDto;
import com.pragma.powerup.application.dto.response.PlatoResponseDto;
import com.pragma.powerup.domain.model.Plato;

import java.util.List;

public interface IPlatoHandler {

    PlatoResponseDto savePlato (PlatoRequestDto platoRequestDto);

    List<PlatoResponseDto> getAllPlatos();

    GenericoPaginadoResponseDto<PlatoResponseDto> listarPlatos(PlatoPaginadoRequestDto platoPaginadoRequestDto);

    PlatoResponseDto updateDescripcionYPrecio (PlatoActualizaRequestDto platoActualizaRequestDto);

    PlatoResponseDto cambiarEstado(Long id);

    PlatoResponseDto habilitarPlato (Long id);

    PlatoResponseDto deshabilitarPlato (Long id);

}
