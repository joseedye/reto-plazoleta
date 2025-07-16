package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Pedido;

import java.util.List;

public interface IPedidoPersistencePort {


    boolean existePedidoEnProcesoParaElCliente(Long ClienteId);

    Pedido guardarPedido(Pedido pedido);

    List<Pedido> listarPedidos(String estado,int pagina,int tamanio,Long restauranteId);

}
