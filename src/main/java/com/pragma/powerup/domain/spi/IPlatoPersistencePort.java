package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.GenericoPaginadoOut;
import com.pragma.powerup.domain.model.PedidoDetalle;
import com.pragma.powerup.domain.model.Plato;
import com.pragma.powerup.domain.model.PlatoPaginado;

import java.util.List;

public interface IPlatoPersistencePort {

    Plato savePlato (Plato plato);
    List<Plato> getAllPlatos();
    GenericoPaginadoOut<Plato> listarPlatos(PlatoPaginado platoPaginado);
    Plato getPlato(Long id);
    Plato updatePlato(Plato plato);
    boolean todosPertenecenARestaurante(Long restautanteId,List<PedidoDetalle> platos );
}
