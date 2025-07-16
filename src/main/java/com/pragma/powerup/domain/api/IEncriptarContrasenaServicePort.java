package com.pragma.powerup.domain.api;

public interface IEncriptarContrasenaServicePort {

    String encriptar(String contrasena);
    boolean compara(String contrasena, String contrasenaEncriptada);

}
