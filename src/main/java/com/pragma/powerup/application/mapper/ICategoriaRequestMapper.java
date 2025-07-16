package com.pragma.powerup.application.mapper;


import com.pragma.powerup.application.dto.request.CategoriaRequestDto;
import com.pragma.powerup.domain.model.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface ICategoriaRequestMapper {

    Categoria toCategoria(CategoriaRequestDto categoriaRequestDto);

}
