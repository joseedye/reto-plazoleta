package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.UsuarioRestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsuarioRestauranteRepository extends JpaRepository<UsuarioRestauranteEntity, Long> {

    Optional<UsuarioRestauranteEntity> findByUsuario_Id(Long UsuarioId);


}
