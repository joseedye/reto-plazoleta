package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.PedidoRequestDto;
import com.pragma.powerup.application.dto.response.PedidoResponseDto;

import java.util.List;

public interface IPedidoHandler {

    PedidoResponseDto savePedido (PedidoRequestDto pedidoRequestDto);

    List<PedidoResponseDto> listarPedidos(String estado, int pagina, int tamanio);
}
