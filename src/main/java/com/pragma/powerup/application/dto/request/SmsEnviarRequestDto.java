package com.pragma.powerup.application.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SmsEnviarRequestDto {

    @NotNull(message = "El telefono es obligatorio")
    private String telefono;
    @NotNull(message = "El mensaje es obligatorio")
    private String  mensaje;

}
