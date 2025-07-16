package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Restaurante;
import com.pragma.powerup.domain.model.Usuario;

import java.util.List;

public interface IRestaurantePersistencePort {

    Restaurante saveRestaurante(Restaurante restaurante);

    List<Restaurante> listarRestaurantes(int pagina,int tamanio);

    Restaurante getRestaurante (Long id);

    void updateRestaurante(Restaurante restaurante);

    void deleteRestaurante(Long id);

    Restaurante getByPropietario(Long id);

}
