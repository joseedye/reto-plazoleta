package com.pragma.powerup.infrastructure.security.adapter;

import com.pragma.powerup.domain.api.IUsuarioSesionServicePort;
import com.pragma.powerup.infrastructure.exception.UsuarioSesionNoFoundException;
import com.pragma.powerup.infrastructure.security.UsuarioDetalles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UsuarioSesionAdapter  implements IUsuarioSesionServicePort {


    @Override
    public Long obtenerIdUsuarioAutenticado() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();

        if (principal instanceof UsuarioDetalles) {
            return ((UsuarioDetalles) principal).getId();
        }

        throw new UsuarioSesionNoFoundException("No se pudo obtener el ID del usuario autenticado");

    }
}
