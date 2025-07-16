package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.LoginRequestDto;
import com.pragma.powerup.application.dto.response.LoginResponseDto;
import com.pragma.powerup.application.handler.ILoginHandler;
import com.pragma.powerup.application.mapper.ILoginResponseMapper;
import com.pragma.powerup.domain.api.ILoginServicePort;
import com.pragma.powerup.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor

public class LoginHandler implements ILoginHandler {

    private final ILoginServicePort loginServicePort;
    private final ILoginResponseMapper loginResponseMapper;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Usuario usuario = loginServicePort.login(loginRequestDto.getCorreo(),loginRequestDto.getClave());
        return loginResponseMapper.toResponse(usuario);
    }
}
