package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.ICategoriaServicePort;
import com.pragma.powerup.domain.model.Categoria;
import com.pragma.powerup.domain.spi.ICategoriaPersistencePort;
import com.pragma.powerup.domain.spi.IUsuarioPersistencePort;

import java.util.List;

public class CategoriaUseCase implements ICategoriaServicePort {

    private final ICategoriaPersistencePort categoriaPersistencePort;

    public CategoriaUseCase(ICategoriaPersistencePort categoriaPersistencePort) {
        this.categoriaPersistencePort = categoriaPersistencePort;
    }

    @Override
    public void saveCategoria(Categoria categoria) {

    }

    @Override
    public List<Categoria> getAllCategorias() {
        return List.of();
    }

    @Override
    public Categoria getCategoria(Long id) {
        return this.categoriaPersistencePort.getCategoria(id);
    }
}
