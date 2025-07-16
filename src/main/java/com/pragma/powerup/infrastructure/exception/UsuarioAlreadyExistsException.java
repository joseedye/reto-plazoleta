package com.pragma.powerup.infrastructure.exception;

public class UsuarioAlreadyExistsException extends RuntimeException{

    public UsuarioAlreadyExistsException(){
        super("Usuario ya existe");
    }

    public UsuarioAlreadyExistsException(String mensaje){
        super(mensaje);
    }
}
