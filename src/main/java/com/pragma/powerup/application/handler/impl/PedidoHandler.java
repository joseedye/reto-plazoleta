package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.PedidoRequestDto;
import com.pragma.powerup.application.dto.response.PedidoResponseDto;
import com.pragma.powerup.application.handler.IPedidoHandler;
import com.pragma.powerup.application.mapper.IPedidoRequestMapper;
import com.pragma.powerup.application.mapper.IPedidoResponseMapper;
import com.pragma.powerup.domain.api.IPedidoServicePort;
import com.pragma.powerup.domain.model.Pedido;
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
    public List<PedidoResponseDto> listarPedidos(String estado, int pagina, int tamanio) {
        return pedidoResponseMapper.toResponseList(pedidoServicePort.listarPedidos(estado, pagina, tamanio));
    }
}
