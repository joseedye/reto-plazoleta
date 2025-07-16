package com.pragma.powerup.infrastructure.exception;

public class CategoriaNotFoundException extends RuntimeException {

  public CategoriaNotFoundException() {
    super();
  }

    public CategoriaNotFoundException(String message) {
        super(message);
    }

}
