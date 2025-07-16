package com.pragma.powerup.infrastructure.input.rest;


import com.pragma.powerup.application.dto.request.LoginRequestDto;
import com.pragma.powerup.application.dto.response.LoginResponseDto;
import com.pragma.powerup.application.handler.ILoginHandler;
import com.pragma.powerup.infrastructure.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1/login")
@RequiredArgsConstructor
public class LoginRestController {

    private final ILoginHandler authHandler;
    private final JwtProvider jwtProvider;

    @PostMapping("/")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        LoginResponseDto response = authHandler.login(loginRequestDto);
        System.out.println("INICIA SESION EL ROL:"+response.getNombreRol());
        String token = jwtProvider.createToken(response.getUsuarioId().toString(),response.getNombreRol());
        return ResponseEntity.ok(Collections.singletonMap("token", token));

    }


}
