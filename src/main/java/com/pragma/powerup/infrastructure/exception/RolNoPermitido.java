package com.pragma.powerup.infrastructure.exception;

public class RolNoPermitido extends RuntimeException {

  public RolNoPermitido (){
    super("El rol especificado no tiene permitida esta accion");
  }

    public RolNoPermitido(String message) {
        super(message);
    }
}
