package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Administrador;
import com.pragma.powerup.domain.model.ObjectModel;
import com.pragma.powerup.domain.model.Usuario;

import java.util.List;

public interface IAdministradorServicePort {

    void saveAdministrador(Usuario usuario);

    List<Administrador> getAllUsuarios();

    Administrador getAdministrador (Long documentoIdentidad);

    void updateAdministrador(Usuario usuario);

    void deleteAdministrador(Long id);
}