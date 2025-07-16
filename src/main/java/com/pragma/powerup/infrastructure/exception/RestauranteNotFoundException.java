package com.pragma.powerup.infrastructure.exception;


public class RestauranteNotFoundException extends RuntimeException {

  public RestauranteNotFoundException()
  {
    super("Restaurante no encontrado");
  }

    public RestauranteNotFoundException(String message) {
        super(message);
    }
}
