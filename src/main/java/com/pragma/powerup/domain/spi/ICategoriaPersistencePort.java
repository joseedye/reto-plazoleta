package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Categoria;

import java.util.List;

public interface ICategoriaPersistencePort {

    void saveCategoria(Categoria categoria);

    List<Categoria> getAllCategorias();

    Categoria getCategoria (Long id);

}
