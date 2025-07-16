package com.pragma.powerup.application.dto.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class PedidoRequestDto {
    @NotNull(message = "El restauranteId es obligatorio")
    private Long restauranteId;
    @NotNull(message = "Los platos del pedido son obligatorios")
    private List<PedidoDetalleRequestDto> detalles;

}

