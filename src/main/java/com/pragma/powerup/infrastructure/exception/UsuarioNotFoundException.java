package com.pragma.powerup.infrastructure.exception;

public class UsuarioNotFoundException extends RuntimeException{
    public UsuarioNotFoundException(){
        super("Usuario no encontrado");
    }

    public UsuarioNotFoundException(String mensaje){
        super(mensaje);
    }
}
