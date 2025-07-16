package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Restaurante;
import com.pragma.powerup.domain.model.UsuarioRestaurante;

public interface IUsuarioRestaurantePersistencePort {

    void saveUsuarioRestaurante(UsuarioRestaurante usuarioRestaurante);

    UsuarioRestaurante getRestauranteEmpleo(Long EmpleadoId);

}
