package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.PedidoPaginadoRequestDto;
import com.pragma.powerup.application.dto.request.PedidoRequestDto;
import com.pragma.powerup.application.dto.response.GenericoPaginadoResponseDto;
import com.pragma.powerup.application.dto.response.PedidoResponseDto;

import java.util.List;

public interface IPedidoHandler {

    PedidoResponseDto savePedido (PedidoRequestDto pedidoRequestDto);

    GenericoPaginadoResponseDto<PedidoResponseDto> listarPedidos(PedidoPaginadoRequestDto pedidoPaginadoRequestDto);

    PedidoResponseDto asignarPedido(Long pedidoId);

    PedidoResponseDto marcarPedidoComoListo(Long pedidoId);
}
