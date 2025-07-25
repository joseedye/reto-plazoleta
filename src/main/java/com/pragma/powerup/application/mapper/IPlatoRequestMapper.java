package com.pragma.powerup.application.mapper;


import com.pragma.powerup.application.dto.request.PlatoActualizaRequestDto;
import com.pragma.powerup.application.dto.request.PlatoPaginadoRequestDto;
import com.pragma.powerup.application.dto.request.PlatoRequestDto;
import com.pragma.powerup.domain.model.Plato;
import com.pragma.powerup.domain.model.PlatoPaginado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface IPlatoRequestMapper {
    Plato toPlato (PlatoRequestDto platoRequestDto);



    @Mapping(target = "id", source = "idPlato")
    @Mapping(target = "nombre", ignore = true)
    @Mapping(target = "urlImagen", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "restaurante", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    Plato toPlatoActualizaRequestDto (PlatoActualizaRequestDto platoActualizaRequestDto);

    PlatoPaginado toPlatoPaginado(PlatoPaginadoRequestDto platoPaginadoRequestDto);


}
