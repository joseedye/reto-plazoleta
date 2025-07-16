package com.pragma.powerup.infrastructure.exception;

public class RolNotFoundException extends RuntimeException{
    public RolNotFoundException (){
        super("El rol especificado no existe");
    }

    public RolNotFoundException(String message) {
        super(message);
    }

}
