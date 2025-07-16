package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.PlatoActualizaRequestDto;
import com.pragma.powerup.application.dto.request.PlatoRequestDto;
import com.pragma.powerup.application.dto.response.PlatoResponseDto;
import com.pragma.powerup.application.exception.NoDataFoundException;
import com.pragma.powerup.application.handler.IPlatoHandler;
import com.pragma.powerup.application.mapper.IPlatoRequestMapper;
import com.pragma.powerup.application.mapper.IPlatoResponseMapper;
import com.pragma.powerup.domain.api.ICategoriaServicePort;
import com.pragma.powerup.domain.api.IPlatoServicePort;
import com.pragma.powerup.domain.api.IRestauranteServicePort;
import com.pragma.powerup.domain.api.IUsuarioServicePort;
import com.pragma.powerup.domain.model.Categoria;
import com.pragma.powerup.domain.model.Plato;
import com.pragma.powerup.domain.model.Restaurante;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class PlatoHandler implements IPlatoHandler {

    private final IPlatoServicePort platoServicePort;
    private final IPlatoRequestMapper platoRequestMapper;
    private final IPlatoResponseMapper platoResponseMapper;
    private final ICategoriaServicePort categoriaServicePort;
    private final IRestauranteServicePort restauranteServicePort;
    private final IUsuarioServicePort usuarioServicePort;

    @Override
    public PlatoResponseDto savePlato(PlatoRequestDto platoRequestDto) {

        Plato plato = platoRequestMapper.toPlato(platoRequestDto);

        Categoria categoria = categoriaServicePort.getCategoria(platoRequestDto.getCategoriaid());
        if(categoria == null){
              throw new NoDataFoundException("La categoria con ID " + platoRequestDto.getCategoriaid() + " no existe");
        }

       plato.setCategoria(categoria);

       Restaurante restaurante = restauranteServicePort.getRestaurante(platoRequestDto.getRestauranteid());
       if(restaurante==null){
            throw new NoDataFoundException("El restaurante con ID " + platoRequestDto.getRestauranteid() + " no existe");
       }
        //caso uso
       plato.setRestaurante(restaurante);
       plato.setEstado(true);
       Plato platoGuardado = platoServicePort.savePlato(plato);
       return platoResponseMapper.toResponse(platoGuardado);
    }

    @Override
    public List<PlatoResponseDto> getAllPlatos() {
        return platoResponseMapper.toResponseList(platoServicePort.getAllPlatos());
    }

    @Override
    public List<PlatoResponseDto> listarPlatos(int pagina, int tamanio, Long categoria) {
        return platoResponseMapper.toResponseList(platoServicePort.listarPlatos(pagina,tamanio,categoria));
    }

    @Override
    public PlatoResponseDto updateDescripcionYPrecio(PlatoActualizaRequestDto platoActualizaRequestDto) {
        Plato plato = platoRequestMapper.toPlatoActualizaRequestDto(platoActualizaRequestDto);
        return platoResponseMapper.toResponse(platoServicePort.updatePlato(plato));
    }

    @Override
    public PlatoResponseDto habilitarPlato(Long id) {
        return platoResponseMapper.toResponse(platoServicePort.habilitarPlato(id));
    }

    @Override
    public PlatoResponseDto deshabilitarPlato(Long id) {
        return platoResponseMapper.toResponse(platoServicePort.deshabilitarPlato(id));
    }
}
