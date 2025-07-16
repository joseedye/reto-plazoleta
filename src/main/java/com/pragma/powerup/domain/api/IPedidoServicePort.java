package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Pedido;

import java.util.List;

public interface IPedidoServicePort {

    Pedido crearPedido(Pedido pedido);

    List<Pedido> listarPedidos(String estado, int pagina, int tamanio);


}
