package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.PedidoResponseDto;
import com.pragma.powerup.domain.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface IPedidoResponseMapper {

    List<PedidoResponseDto> toResponseList(List<Pedido> pedidos);

    PedidoResponseDto toResponseDto(Pedido pedido);

}
