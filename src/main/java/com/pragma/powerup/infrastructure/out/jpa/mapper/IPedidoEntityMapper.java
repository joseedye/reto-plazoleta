package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.infrastructure.out.jpa.entity.PedidoEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestauranteEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = IDetallePedidoEntityMapper.class
)


public interface IPedidoEntityMapper {

        @org.mapstruct.Mapping(target = "restaurante", source = "restauranteId", qualifiedByName = "mapRestaurante")
        @org.mapstruct.Mapping(target = "usuarioEntity", source = "clienteId", qualifiedByName = "mapUsuario")
        PedidoEntity toEntity(Pedido pedido);

        @org.mapstruct.Mapping(target = "restauranteId", source = "restaurante", qualifiedByName = "mapRestauranteToId")
        @org.mapstruct.Mapping(target = "clienteId", source = "usuarioEntity", qualifiedByName = "mapUsuarioToId")
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
