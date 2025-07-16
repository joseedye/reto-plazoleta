package com.pragma.powerup.application.dto.response;

import com.pragma.powerup.application.dto.request.RolDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class ClienteResponseDto {

    private Long id;
    private String nombres;
    private String apellidos;
    private String documentoIdentidad;
    private String celular;
    private String correo;
    private String clave;
    private RolDto rol;

}
