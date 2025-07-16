package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.Categoria;
import com.pragma.powerup.domain.model.Usuario;
import com.pragma.powerup.infrastructure.out.jpa.entity.CategoriaEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ICategoriaEntityMapper {

    CategoriaEntity toEntity(Categoria categoria);


    Categoria toCategoria(CategoriaEntity categoriaEntity);

    List<Categoria> toCategoriaList(List<CategoriaEntity> CategoriaEntityList);



}
