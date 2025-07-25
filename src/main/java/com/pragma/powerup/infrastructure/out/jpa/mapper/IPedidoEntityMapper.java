package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.infrastructure.out.jpa.entity.PedidoEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestauranteEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = { IDetallePedidoEntityMapper.class } //, IPedidoEntityMapper.class
)


public interface IPedidoEntityMapper {

        @Mapping(target = "restaurante", source = "restauranteId", qualifiedByName = "mapRestaurante")
        @Mapping(target = "usuarioEntity", source = "clienteId", qualifiedByName = "mapUsuario")
        @Mapping(target = "pinSeguridad", source = "pinSeguridad")
        PedidoEntity toEntity(Pedido pedido);

        @Mapping(target = "restauranteId", source = "restaurante", qualifiedByName = "mapRestauranteToId")
        @Mapping(target = "clienteId", source = "usuarioEntity", qualifiedByName = "mapUsuarioToId")
        //@Mapping(target = "empleadoAsignado", source = "empleadoAsignado", qualifiedByName = "mapUsuarioToId")
        Pedido toPedido(PedidoEntity entity);

        @Named("mapRestaurante")
        default RestauranteEntity mapRestaurante(Long id) {
                if (id == null) return null;
                RestauranteEntity restaurante = new RestauranteEntity();
                restaurante.setId(id);
                return restaurante;
        }

        @Named("mapUsuario")
        default UsuarioEntity mapUsuario(Long id) {
                if (id == null) return null;
                UsuarioEntity usuario = new UsuarioEntity();
                usuario.setId(id);
                return usuario;
        }


        @Named("mapRestauranteToId")
        default Long mapRestauranteToId(RestauranteEntity restaurante) {
                return (restaurante != null) ? restaurante.getId(): null;
        }

        @Named("mapUsuarioToId")
        default Long mapUsuarioToId(UsuarioEntity usuarioEntity) {
                return (usuarioEntity != null) ? usuarioEntity.getId() : null;
        }



}
