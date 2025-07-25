package com.pragma.powerup.domain.spi;


import com.pragma.powerup.domain.model.GenericoPaginadoOut;
import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.domain.model.PedidoPaginado;

import java.util.List;

public interface IPedidoPersistencePort {


    boolean existePedidoEnProcesoParaElCliente(Long ClienteId);

    Pedido guardarPedido(Pedido pedido);

    GenericoPaginadoOut<Pedido> listarPedidos(PedidoPaginado pedidoPaginado, Long restauranteId);

    Pedido getPedido(Long idPedido);

    Pedido actualizarPedido(Pedido pedido);
}
