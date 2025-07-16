package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Administrador;
import com.pragma.powerup.domain.model.ObjectModel;
import com.pragma.powerup.domain.model.Usuario;

import java.util.List;

public interface IAdministradorPersistencePort {

    void saveAdministrador(Administrador administrador);

    List<Administrador> getAllAdministradors();

    Administrador getAdministrador (Long documentoIdentidad);

    void updateAdministrador(Administrador administrador);

    void deleteAdministrador(Long id);




}