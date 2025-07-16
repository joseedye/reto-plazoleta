package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Categoria;
import com.pragma.powerup.domain.spi.ICategoriaPersistencePort;
import com.pragma.powerup.infrastructure.exception.CategoriaNotFoundException;
import com.pragma.powerup.infrastructure.exception.UsuarioNotFoundException;
import com.pragma.powerup.infrastructure.out.jpa.mapper.ICategoriaEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IUsuarioEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.ICategoriaRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CategoriaJpaAdapter implements ICategoriaPersistencePort {

    private final ICategoriaRepository categoriaRepository;
    private final ICategoriaEntityMapper categoriaEntityMapper;

    @Override
    public void saveCategoria(Categoria categoria) {
//        if(categoriaRepository.findByid(categoria.getId())){
//
//        }
    }

    @Override
    public List<Categoria> getAllCategorias() {
        return List.of();
    }

    @Override
    public Categoria getCategoria(Long id) {
        return categoriaEntityMapper.toCategoria(categoriaRepository.findByid(id)
                .orElseThrow(CategoriaNotFoundException::new));
    }
}
