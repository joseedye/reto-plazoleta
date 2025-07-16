package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.ClienteRequestDto;
import com.pragma.powerup.application.dto.request.EmpleadoRequestDto;
import com.pragma.powerup.application.dto.request.UsuarioRequestDto;
import com.pragma.powerup.application.dto.response.ClienteResponseDto;
import com.pragma.powerup.application.dto.response.EmpleadoResponseDto;
import com.pragma.powerup.application.dto.response.UsuarioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IUsuarioHandler {

    UsuarioResponseDto saveUsuario(UsuarioRequestDto usuarioRequestDto);

    UsuarioResponseDto savePropietario(UsuarioRequestDto usuarioRequestDto);

    EmpleadoResponseDto saveEmpleado(EmpleadoRequestDto empleadoRequestDto);

    ClienteResponseDto saveCliente(ClienteRequestDto clienteRequestDto);

    List<UsuarioResponseDto> getAllUsuarios();


}