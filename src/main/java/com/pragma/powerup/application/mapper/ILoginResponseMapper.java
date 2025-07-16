package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.CategoriaRequestDto;
import com.pragma.powerup.application.dto.request.RestauranteRequestDto;
import com.pragma.powerup.application.dto.response.LoginResponseDto;
import com.pragma.powerup.domain.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CategoriaRequestDto.class, RestauranteRequestDto.class}
)
public interface ILoginResponseMapper {

    @Mapping(source = "id", target = "usuarioId")
    @Mapping(source = "rol.descripcion", target = "nombreRol")
    LoginResponseDto toResponse(Usuario usuario);

}
