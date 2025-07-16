package com.pragma.powerup.application.dto.request;


import com.pragma.powerup.application.validation.annotation.ValidarEdad;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
public class UsuarioRequestDto {
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    private String nombres;
    @NotBlank(message = "El apellido es obligatorio")
    private String apellidos;


    @Pattern(regexp = "^\\d{5,15}$", message = "El número de documento debe contener solo números entre 5 y 15 dígitos")
    @NotNull(message = "El número de documento es obligatorio")
    private String documentoIdentidad;

    @NotBlank(message = "El numero de celular es obligatorio")
    @Pattern(regexp = "^\\+?\\d{7,13}$", message = "El número de teléfono debe contener solo números, maximo 13  y debe empezar con +")
    private String celular;


    @NotNull(message = "La fecha de nacimiento es obligatorio")
    @Past(message = "La fecha debe ser anterior a la fecha actual")
    @ValidarEdad(message = "El usuario debe ser mayor de edad")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo electrónico no tiene un formato válido")
    private String correo;
    @NotBlank(message = "La contraseña es obligatoria")
    private String clave;

    //@NotNull(message = "El rol es obligatorio")
    private Long rolId;




}
