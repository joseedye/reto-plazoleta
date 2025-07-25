package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PedidoPaginadoRequestDto {

    @NotNull(message = "El estado es obligatorio")
    private String estado;
    private int pagina = 0;
    private int tamanio = 10;

}
