package com.pragma.powerup.domain.api;

import com.pragma.powerup.application.dto.request.LoginRequestDto;
import com.pragma.powerup.application.dto.response.LoginResponseDto;
import com.pragma.powerup.domain.model.Usuario;

public interface ILoginServicePort {

    public Usuario login(String correo, String  contrasena);

}
