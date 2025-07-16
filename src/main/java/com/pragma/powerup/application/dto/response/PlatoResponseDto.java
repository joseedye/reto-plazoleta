package com.pragma.powerup.application.dto.response;

import com.pragma.powerup.application.dto.request.CategoriaRequestDto;
import com.pragma.powerup.application.dto.request.RestauranteRequestDto;
import com.pragma.powerup.domain.model.Categoria;
import com.pragma.powerup.domain.model.Restaurante;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatoResponseDto {

    private Long id;
    private String nombre;
    private Long precio;
    private String descripcion;
    private String urlImagen;
    private boolean estado;
    private RestauranteResponseDto restaurante;
    private CategoriaResponseDto categoria;


}
