package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.RestauranteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRestauranteRepository  extends JpaRepository<RestauranteEntity, Long> {

    Optional<RestauranteEntity> findByid(Long id);

    Optional<RestauranteEntity> findBypropietario_id  (Long id);

    void deleteById (Long id);

    Page<RestauranteEntity> findAllByOrderByNombreAsc(Pageable pageable);
}
