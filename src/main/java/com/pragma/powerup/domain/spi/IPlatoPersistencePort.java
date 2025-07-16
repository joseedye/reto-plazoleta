package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.PedidoDetalle;
import com.pragma.powerup.domain.model.Plato;

import java.util.List;

public interface IPlatoPersistencePort {

    Plato savePlato (Plato plato);
    List<Plato> getAllPlatos();
    List<Plato> listarPlatos(int pagina,int tamanio,Long categoria);
    Plato getPlato(Long id);
    Plato updatePlato(Plato plato);
    boolean todosPertenecenARestaurante(Long restautanteId,List<PedidoDetalle> platos );
}
