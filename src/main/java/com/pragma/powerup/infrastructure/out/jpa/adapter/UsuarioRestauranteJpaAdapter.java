package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.UsuarioRestaurante;
import com.pragma.powerup.domain.spi.IUsuarioRestaurantePersistencePort;
import com.pragma.powerup.infrastructure.exception.UsuarioSinPermisoException;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IUsuarioRestauranteEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUsuarioRestauranteRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UsuarioRestauranteJpaAdapter implements IUsuarioRestaurantePersistencePort {


    private final IUsuarioRestauranteRepository usuarioRestauranteRepository;
    private final IUsuarioRestauranteEntityMapper usuarioRestauranteEntityMapper;

    @Override
    public void saveUsuarioRestaurante(UsuarioRestaurante usuarioRestaurante) {
        usuarioRestauranteRepository.save(usuarioRestauranteEntityMapper.toEntity(usuarioRestaurante));
    }

    @Override
    public UsuarioRestaurante getRestauranteEmpleo(Long EmpleadoId) {
        return usuarioRestauranteEntityMapper.toUsuario(usuarioRestauranteRepository.
                findByUsuario_Id(EmpleadoId)
                .orElseThrow(UsuarioSinPermisoException::new));

    }
}
