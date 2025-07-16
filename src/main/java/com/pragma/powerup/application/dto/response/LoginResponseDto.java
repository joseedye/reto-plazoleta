package com.pragma.powerup.application.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private Long usuarioId;
    private String nombreRol;

    public LoginResponseDto(Long usuarioId, String nombreRol) {
        this.usuarioId = usuarioId;
        this.nombreRol = nombreRol;
    }
}
