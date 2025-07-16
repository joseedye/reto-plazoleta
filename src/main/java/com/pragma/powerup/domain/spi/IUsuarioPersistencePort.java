package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Administrador;
import com.pragma.powerup.domain.model.ObjectModel;
import com.pragma.powerup.domain.model.Usuario;
import com.pragma.powerup.infrastructure.out.jpa.entity.UsuarioEntity;

import java.util.List;

public interface IUsuarioPersistencePort {

    Usuario saveUsuario(Usuario usuario);

    List<Usuario> getAllUsuarios();

    Usuario getUsuario (String documentoIdentidad);

    Usuario getUsuario (Long id);

    Usuario findByCorreo (String correo);

    boolean existsByCorreo(String correo);

    boolean existsByDocumento(String documento);

    void updateUsuario(Usuario usuario);

    void deleteUsuario(String id);

}