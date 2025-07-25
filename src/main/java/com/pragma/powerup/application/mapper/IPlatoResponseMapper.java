package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.CategoriaRequestDto;
import com.pragma.powerup.application.dto.request.RestauranteRequestDto;
import com.pragma.powerup.application.dto.response.GenericoPaginadoResponseDto;
import com.pragma.powerup.application.dto.response.PlatoResponseDto;
import com.pragma.powerup.domain.model.GenericoPaginadoOut;
import com.pragma.powerup.domain.model.Plato;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CategoriaRequestDto.class, RestauranteRequestDto.class}
)
public interface IPlatoResponseMapper {
    PlatoResponseDto toResponse(Plato plato);
    List<PlatoResponseDto> toResponseList(List<Plato> platos);

    GenericoPaginadoResponseDto<PlatoResponseDto> toGenericoPlatoList (GenericoPaginadoOut<Plato> genericoPaginadoOut);


}
