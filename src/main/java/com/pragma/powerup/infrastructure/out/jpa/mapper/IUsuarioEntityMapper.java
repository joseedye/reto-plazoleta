package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.ObjectModel;
import com.pragma.powerup.domain.model.Usuario;
import com.pragma.powerup.infrastructure.out.jpa.entity.ObjectEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IUsuarioEntityMapper {

    @Mapping(source = "id", target = "id")
    UsuarioEntity toEntity(Usuario usuario);

    @Mapping(source = "id", target = "id")
    Usuario toUsuario(UsuarioEntity usuarioEntity);

    List<Usuario> toUsuarioList(List<UsuarioEntity> UsuarioEntityList);
}