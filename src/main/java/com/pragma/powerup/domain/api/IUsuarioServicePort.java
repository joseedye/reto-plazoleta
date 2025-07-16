package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.ObjectModel;
import com.pragma.powerup.domain.model.Usuario;

import java.util.List;

public interface IUsuarioServicePort {

    Usuario saveUsuario(Usuario usuario,Long rol);

    List<Usuario> getAllUsuarios();

    Usuario getUsuario (String documentoIdentidad);

    Usuario getUsuario (Long id);


    void updateUsuario(Usuario usuario);

    void deleteUsuario(String id);
}