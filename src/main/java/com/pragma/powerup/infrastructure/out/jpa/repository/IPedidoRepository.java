package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.PedidoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPedidoRepository extends JpaRepository<PedidoEntity,Long> {

    boolean existsByUsuarioEntity_IdAndEstadoIn(Long clienteId, List<String> estados);

    Page<PedidoEntity> findByEstadoAndRestaurante_Id(String estado, Long restauranteId, Pageable pageable);


}




