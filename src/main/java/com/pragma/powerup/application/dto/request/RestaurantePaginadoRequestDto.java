package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RestaurantePaginadoRequestDto {

    private int pagina = 0;
    private int tamanio = 10;

}
