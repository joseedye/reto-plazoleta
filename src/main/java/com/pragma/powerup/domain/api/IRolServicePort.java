package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.ObjectModel;
import com.pragma.powerup.domain.model.Rol;
import com.pragma.powerup.domain.model.Usuario;

import java.util.List;

public interface IRolServicePort {

    void saveRol(Rol rol);

    List<Rol> getAllRol();

    Rol getRol (Long id);

}