package com.pragma.powerup.infrastructure.security;

import com.pragma.powerup.domain.api.IEncriptarContrasenaServicePort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncriptarContrasenaAdapter implements IEncriptarContrasenaServicePort {

    private final PasswordEncoder passwordEncoder;

    public EncriptarContrasenaAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encriptar(String contrasena) {
        return passwordEncoder.encode(contrasena);
    }

    @Override
    public boolean compara(String contrasena, String contrasenaEncriptada) {
        return passwordEncoder.matches(contrasena, contrasenaEncriptada);
    }
}
