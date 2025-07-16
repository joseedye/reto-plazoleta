package com.pragma.powerup.infrastructure.out.jpa.mapper;


import com.pragma.powerup.domain.model.Usuario;
import com.pragma.powerup.domain.model.UsuarioRestaurante;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestauranteEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.UsuarioEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.UsuarioRestauranteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IUsuarioRestauranteEntityMapper {

    @Mapping(target = "usuario", source = "usuarioId", qualifiedByName = "mapUsuario")
    @Mapping(target = "restaurante", source = "restauranteId", qualifiedByName = "mapRestaurante")
    UsuarioRestauranteEntity toEntity(UsuarioRestaurante usuarioRestaurante);

    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "restauranteId", source = "restaurante.id")
    UsuarioRestaurante toUsuario(UsuarioRestauranteEntity usuarioRestaurante);


    @Named("mapUsuario")
    default UsuarioEntity mapUsuario(Long id) {
        if (id == null) return null;
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(id);
        return usuario;
    }

    @Named("mapRestaurante")
    default RestauranteEntity mapRestaurante(Long id) {
        if (id == null) return null;
        RestauranteEntity restaurante = new RestauranteEntity();
        restaurante.setId(id);
        return restaurante;
    }
}
