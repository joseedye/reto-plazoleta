package com.pragma.powerup.application.dto.request;


import com.pragma.powerup.application.validation.annotation.ValidarEdad;
import com.pragma.powerup.application.validation.annotation.ValidarNombreRestaurante;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
public class RestauranteRequestDto {

    private Long id;

    @ValidarNombreRestaurante(message = "El nombre del restaurante no puede estar vacío ni ser solo números")
    private String nombre;

    @Pattern(regexp = "^\\d{5,10}$", message = "El NIT debe contener solo números entre 5 y 10 dígitos")
    @NotBlank(message = "El NIT es obligatorio")
    private String NIT;

    @NotBlank(message = "La direccion es obligatorio")
    private String direccion;

    @NotBlank(message = "El numero de teléfono es obligatorio")
    @Pattern(regexp = "^\\+?\\d{7,13}$", message = "El número de teléfono debe contener solo números, maximo 13  y debe empezar con +")
    private String telefono;

    @NotBlank(message = "El urlLogo es obligatorio")
    private String urlLogo;

    @NotNull(message = "El id del propietario es obligatorio")
    private Long propietarioId;

}
