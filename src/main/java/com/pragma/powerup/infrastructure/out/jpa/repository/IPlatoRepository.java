package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.PlatoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPlatoRepository extends JpaRepository<PlatoEntity,Long> {

    Optional<PlatoEntity> findByrestauranteId(Long restauranteId);
    Optional<PlatoEntity> findByid(Long id);

    Page<PlatoEntity> findAllByCategoria_Id(Long categoriaId, Pageable pageable);
}
