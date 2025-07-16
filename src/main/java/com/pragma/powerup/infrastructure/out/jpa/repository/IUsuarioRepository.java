package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findBydocumentoIdentidad(String usuarioNumero);

    Optional<UsuarioEntity> findByid(Long id);

    Optional<UsuarioEntity> findFirstByCorreo(String correo);

    boolean existsByCorreo(String correo);

    boolean existsBydocumentoIdentidad (String documento);

    void deleteBydocumentoIdentidad (String usuarioNumero);

}