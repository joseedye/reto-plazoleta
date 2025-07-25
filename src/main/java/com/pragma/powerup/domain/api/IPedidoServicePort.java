package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.GenericoPaginadoOut;
import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.domain.model.PedidoPaginado;


public interface IPedidoServicePort {

    Pedido crearPedido(Pedido pedido);

    GenericoPaginadoOut<Pedido> listarPedidos(PedidoPaginado pedidoPaginado);

    Pedido asignarPedido(Long pedidoId);

    Pedido marcarPedidoComoListo(Long pedidoId);

}
