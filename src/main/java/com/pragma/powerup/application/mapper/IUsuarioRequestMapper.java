package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.ClienteRequestDto;
import com.pragma.powerup.application.dto.request.EmpleadoRequestDto;
import com.pragma.powerup.application.dto.request.ObjectRequestDto;
import com.pragma.powerup.application.dto.request.UsuarioRequestDto;
import com.pragma.powerup.domain.model.ObjectModel;
import com.pragma.powerup.domain.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)


public interface IUsuarioRequestMapper
{

    Usuario toUsuario(UsuarioRequestDto usuarioRequestDto);

    Usuario toUsuario(EmpleadoRequestDto empleadoRequestDto);

    Usuario toUsuario(ClienteRequestDto clienteRequestDto);

}
