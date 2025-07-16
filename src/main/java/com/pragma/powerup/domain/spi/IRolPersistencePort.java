package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Administrador;
import com.pragma.powerup.domain.model.ObjectModel;
import com.pragma.powerup.domain.model.Rol;
import com.pragma.powerup.domain.model.Usuario;

import java.util.List;

public interface IRolPersistencePort {

    void saveRol(Rol rol);

    List<Rol> getAllRol();

    Rol getRol (Long id);




}