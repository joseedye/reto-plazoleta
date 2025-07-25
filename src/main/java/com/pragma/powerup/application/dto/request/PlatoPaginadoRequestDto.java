package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PlatoPaginadoRequestDto {

    @NotNull(message = "la categoria es obligatoria")
    private Long categoria;
    private int pagina = 0;
    private int tamanio = 10;

}
