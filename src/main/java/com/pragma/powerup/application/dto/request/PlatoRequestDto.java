package com.pragma.powerup.application.dto.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PlatoRequestDto {


    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El precio es obligatorio")
    private Long precio;

    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;

    @NotBlank(message = "La url de la Imagen es obligatoria")
    private String urlImagen;

    private boolean estado;

    @NotNull(message = "El id del restaurante es obligatorio")
    private Long restauranteid;

    @NotNull(message = "El id de categoria es obligatorio")
    private Long categoriaid;

}
