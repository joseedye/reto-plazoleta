package com.pragma.powerup.domain.exception;

public class UsuarioSinPermisoException extends RuntimeException {
    public UsuarioSinPermisoException(String message) {
        super(message);
    }
}
