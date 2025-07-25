package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.PedidoPaginadoRequestDto;
import com.pragma.powerup.application.dto.request.PedidoRequestDto;
import com.pragma.powerup.application.dto.response.GenericoPaginadoResponseDto;
import com.pragma.powerup.application.dto.response.PedidoResponseDto;
import com.pragma.powerup.application.handler.IPedidoHandler;
import com.pragma.powerup.application.mapper.IPedidoRequestMapper;
import com.pragma.powerup.application.mapper.IPedidoResponseMapper;
import com.pragma.powerup.domain.api.IPedidoServicePort;
import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.domain.model.PedidoPaginado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PedidoHandler implements IPedidoHandler {

    private final IPedidoServicePort pedidoServicePort;
    private final IPedidoRequestMapper pedidoRequestMapper;
    private final IPedidoResponseMapper pedidoResponseMapper;


    @Override
    public PedidoResponseDto savePedido(PedidoRequestDto pedidoRequestDto) {
       Pedido pedido = pedidoRequestMapper.toPedido(pedidoRequestDto);
        return pedidoResponseMapper.toResponseDto(pedidoServicePort.crearPedido(pedido));
    }

    @Override
    public GenericoPaginadoResponseDto<PedidoResponseDto> listarPedidos(PedidoPaginadoRequestDto pedidoPaginadoRequestDto) {
        PedidoPaginado pedidoPaginado = pedidoRequestMapper.toPedidoPaginado(pedidoPaginadoRequestDto);
        return pedidoResponseMapper.toGenericoPedidoList(pedidoServicePort.listarPedidos(pedidoPaginado));
    }

    @Override
    public PedidoResponseDto asignarPedido(Long pedidoId) {
        return pedidoResponseMapper.toResponseDto(pedidoServicePort.asignarPedido(pedidoId));
    }

    @Override
    public PedidoResponseDto marcarPedidoComoListo(Long pedidoId) {
        return pedidoResponseMapper.toResponseDto(pedidoServicePort.marcarPedidoComoListo(pedidoId));
    }
}
