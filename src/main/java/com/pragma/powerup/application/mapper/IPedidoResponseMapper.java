package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.GenericoPaginadoResponseDto;
import com.pragma.powerup.application.dto.response.PedidoResponseDto;
import com.pragma.powerup.domain.model.GenericoPaginadoOut;
import com.pragma.powerup.domain.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface IPedidoResponseMapper {


    @Mapping(target = "clienteId", source = "clienteId")

    PedidoResponseDto toResponseDto(Pedido pedido);

    List<PedidoResponseDto> toResponseList(List<Pedido> pedidos);

    GenericoPaginadoResponseDto<PedidoResponseDto>  toGenericoPedidoList (GenericoPaginadoOut<Pedido> genericopedido);


}
