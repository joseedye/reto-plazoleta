package com.pragma.powerup.application.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GenericoPaginadoResponseDto<T> {

    private List<T> lista;
    private int pagina;
    private int tamanio;
    private long totalElementos;
    private int totalPaginas;

}
