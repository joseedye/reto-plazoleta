package com.pragma.powerup.application.dto.response;

import com.pragma.powerup.domain.model.PedidoDetalle;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class PedidoResponseDto {


    private Long id;
    private Long clienteId;
    private Long restauranteId;
    private String estado;
    private Long pinSeguridad;
    private List<PedidoDetalle> detalles;


}
