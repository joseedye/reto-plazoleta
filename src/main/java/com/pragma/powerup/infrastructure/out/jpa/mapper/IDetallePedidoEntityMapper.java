package com.pragma.powerup.infrastructure.out.jpa.mapper;


import com.pragma.powerup.domain.model.PedidoDetalle;
import com.pragma.powerup.infrastructure.out.jpa.entity.DetallePedidoEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.PlatoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)

public interface IDetallePedidoEntityMapper {

    @Mapping(target = "plato", source = "platoId", qualifiedByName = "mapPlato")
    DetallePedidoEntity toEntity(PedidoDetalle detalle);


    @Mapping(target = "pedidoId", source = "pedido.id")
    @Mapping(target = "platoId", source = "plato.id")
    PedidoDetalle toDetallePedido(DetallePedidoEntity entity);

    @Named("mapPlato")
    default PlatoEntity mapPlato(Long id) {
        if (id == null) return null;
        PlatoEntity plato = new PlatoEntity();
        plato.setId(id);
        return plato;
    }

}
