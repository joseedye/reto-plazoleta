package com.pragma.powerup.domain.model;

import java.time.LocalDate;

public class Propietario extends Usuario{
    public Propietario(Long id, String nombres, String apellidos, String documentoIdentidad, String celular, LocalDate fechaNacimiento, String correo, String clave) {
        super(id, nombres, apellidos, documentoIdentidad, celular, fechaNacimiento, correo, clave,null,0);
    }
}
