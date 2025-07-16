package com.pragma.powerup.application.dto.response;


import com.pragma.powerup.application.dto.request.UsuarioRequestDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteResponseDto {

    private Long id;
    private String nombre;
//    private String NIT;
//    private String direccion;
//    private String telefono;
    private String urlLogo;
   // private UsuarioRequestDto propietario;

}
