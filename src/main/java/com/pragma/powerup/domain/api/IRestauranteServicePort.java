package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Restaurante;
import com.pragma.powerup.domain.model.Usuario;

import java.util.List;

public interface IRestauranteServicePort {

    Restaurante saveRestaurante(Restaurante restaurante);

    List<Restaurante> listarRestaurantes(int pagina,int tamanio );

    Restaurante getRestaurante (Long id);

    void updateRestaurante(Restaurante restaurante);

    void deleteRestaurante(Long id);



}
