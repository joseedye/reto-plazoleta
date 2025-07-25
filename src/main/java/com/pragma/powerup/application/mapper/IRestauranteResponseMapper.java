package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.RolDto;
import com.pragma.powerup.application.dto.request.UsuarioRequestDto;
import com.pragma.powerup.application.dto.response.GenericoPaginadoResponseDto;
import com.pragma.powerup.application.dto.response.RestauranteResponseDto;
import com.pragma.powerup.application.dto.response.UsuarioResponseDto;
import com.pragma.powerup.domain.model.GenericoPaginadoOut;
import com.pragma.powerup.domain.model.Restaurante;
import com.pragma.powerup.domain.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {UsuarioRequestDto.class})

public interface IRestauranteResponseMapper {

    IUsuarioRequestMapper INSTANCE = Mappers.getMapper(IUsuarioRequestMapper.class);

    RestauranteResponseDto toResponse(Restaurante restaurante);

    List<RestauranteResponseDto> toResponseList(List<Restaurante> restaurante);

    GenericoPaginadoResponseDto<RestauranteResponseDto> toResponseGenerico(GenericoPaginadoOut<Restaurante> entidad);


}
