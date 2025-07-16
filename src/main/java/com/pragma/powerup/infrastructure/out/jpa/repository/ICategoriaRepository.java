package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.CategoriaEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICategoriaRepository extends JpaRepository<CategoriaEntity,Long> {

    Optional<CategoriaEntity> findBydescripcion(String descripcion);

    Optional<CategoriaEntity> findByid(Long id);

}
