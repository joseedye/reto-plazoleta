package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
public class PedidoDetalleRequestDto {
    @NotNull(message = "El plato es obligatorio")
    private Long platoId;
    @NotNull(message = "La cantidad es obligatoria")
    private int cantidad;

}
