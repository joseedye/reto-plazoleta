package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.ClienteRequestDto;
import com.pragma.powerup.application.dto.request.EmpleadoRequestDto;
import com.pragma.powerup.application.dto.request.UsuarioRequestDto;
import com.pragma.powerup.application.dto.response.ClienteResponseDto;
import com.pragma.powerup.application.dto.response.EmpleadoResponseDto;
import com.pragma.powerup.application.dto.response.UsuarioResponseDto;
import com.pragma.powerup.application.handler.IUsuarioHandler;
import com.pragma.powerup.application.mapper.*;
import com.pragma.powerup.domain.api.IUsuarioServicePort;
import com.pragma.powerup.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioHandler implements IUsuarioHandler {

    private final IUsuarioServicePort usuarioServicePort;
    private final IUsuarioRequestMapper usuarioRequestMapper;
    private final IUsuarioResponseMapper usuarioResponseMapper;

    @Override
    public UsuarioResponseDto saveUsuario(UsuarioRequestDto usuarioRequestDto) {
        Usuario  usuario  = usuarioRequestMapper.toUsuario(usuarioRequestDto);
        return usuarioResponseMapper.toResponse(usuarioServicePort.saveUsuario(usuario,usuarioRequestDto.getRolId()));
    }

    @Override
    public UsuarioResponseDto savePropietario(UsuarioRequestDto usuarioRequestDto) {
        Usuario  usuario  = usuarioRequestMapper.toUsuario(usuarioRequestDto);
        return usuarioResponseMapper.toResponse(usuarioServicePort.saveUsuario(usuario,2L));
    }

    @Override
    public EmpleadoResponseDto saveEmpleado(EmpleadoRequestDto empleadoRequestDto) {
        Usuario  usuario  = usuarioRequestMapper.toUsuario(empleadoRequestDto);
        return usuarioResponseMapper.toEmpleadoResponseDto(usuarioServicePort.saveUsuario(usuario,3L));
    }

    @Override
    public ClienteResponseDto saveCliente(ClienteRequestDto clienteRequestDto) {
        Usuario  usuario  = usuarioRequestMapper.toUsuario(clienteRequestDto);
       return usuarioResponseMapper.toClienteResponseDto(usuarioServicePort.saveUsuario(usuario,4L));

    }

    @Override
    public List<UsuarioResponseDto> getAllUsuarios() {
        return usuarioResponseMapper.toResponseList(usuarioServicePort.getAllUsuarios());
    }
}