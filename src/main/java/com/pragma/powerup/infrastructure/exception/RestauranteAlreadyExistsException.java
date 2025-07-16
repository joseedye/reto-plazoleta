package com.pragma.powerup.infrastructure.exception;

public class RestauranteAlreadyExistsException extends RuntimeException {

  public RestauranteAlreadyExistsException(){
    super();
  }

    public RestauranteAlreadyExistsException(String message) {
        super(message);
    }

}
