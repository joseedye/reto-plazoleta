package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.RolDto;
import com.pragma.powerup.application.dto.response.ClienteResponseDto;
import com.pragma.powerup.application.dto.response.EmpleadoResponseDto;
import com.pragma.powerup.application.dto.response.ObjectResponseDto;
import com.pragma.powerup.application.dto.response.UsuarioResponseDto;
import com.pragma.powerup.domain.model.ObjectModel;
import com.pragma.powerup.domain.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {RolDto.class})

public interface IUsuarioResponseMapper {

    IRolRequestMapper INSTANCE = Mappers.getMapper(IRolRequestMapper.class);

    UsuarioResponseDto toResponse(Usuario usuario);

    EmpleadoResponseDto toEmpleadoResponseDto(Usuario usuario);

    ClienteResponseDto toClienteResponseDto(Usuario usuario);

    List<UsuarioResponseDto> toResponseList(List<Usuario> usuarios);
}
