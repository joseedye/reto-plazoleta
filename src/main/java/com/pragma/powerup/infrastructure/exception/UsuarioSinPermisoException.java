package com.pragma.powerup.infrastructure.exception;

public class UsuarioSinPermisoException extends RuntimeException {

    public UsuarioSinPermisoException() {

        super("No tienes permisos");
    }

    public UsuarioSinPermisoException(String message) {
        super(message);
    }
}
