package com.pragma.powerup.application.mapper;


import com.pragma.powerup.application.dto.request.PedidoPaginadoRequestDto;
import com.pragma.powerup.application.dto.request.PedidoRequestDto;
import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.domain.model.PedidoPaginado;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)


public interface IPedidoRequestMapper {

    Pedido toPedido(PedidoRequestDto pedidoRequestDto);

    PedidoPaginado toPedidoPaginado(PedidoPaginadoRequestDto pedidoPaginadoRequestDto);


}
