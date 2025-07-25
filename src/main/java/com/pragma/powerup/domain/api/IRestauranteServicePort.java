package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.GenericoPaginadoOut;
import com.pragma.powerup.domain.model.Restaurante;
import com.pragma.powerup.domain.model.RestaurantePaginado;
import com.pragma.powerup.domain.model.Usuario;

import java.util.List;

public interface IRestauranteServicePort {

    Restaurante saveRestaurante(Restaurante restaurante);

    GenericoPaginadoOut<Restaurante> listarRestaurantes(RestaurantePaginado restaurantePaginado);

    Restaurante getRestaurante (Long id);

    void updateRestaurante(Restaurante restaurante);

    void deleteRestaurante(Long id);



}
