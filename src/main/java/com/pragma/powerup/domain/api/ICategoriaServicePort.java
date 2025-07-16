package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Categoria;
import com.pragma.powerup.domain.model.Usuario;

import java.util.Calendar;
import java.util.List;

public interface ICategoriaServicePort {

    void saveCategoria(Categoria categoria);

    List<Categoria> getAllCategorias();
    Categoria getCategoria (Long id);
}
