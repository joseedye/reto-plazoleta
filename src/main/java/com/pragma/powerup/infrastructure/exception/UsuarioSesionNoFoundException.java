package com.pragma.powerup.infrastructure.exception;

public class UsuarioSesionNoFoundException extends RuntimeException {
    public UsuarioSesionNoFoundException(String message) {
        super(message);
    }
}
