package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.domain.spi.IPedidoPersistencePort;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.out.jpa.entity.DetallePedidoEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.PedidoEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IPedidoEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IPedidoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public class PedidoJpaAdapter implements IPedidoPersistencePort {

    private final IPedidoRepository pedidoRepository;
    private final IPedidoEntityMapper pedidoEntityMapper;

    public PedidoJpaAdapter(IPedidoRepository pedidoRepository, IPedidoEntityMapper pedidoEntityMapper) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoEntityMapper = pedidoEntityMapper;
    }

    @Override
    public boolean existePedidoEnProcesoParaElCliente(Long ClienteId) {
        return pedidoRepository.existsByUsuarioEntity_IdAndEstadoIn(ClienteId, List.of("PENDIENTE", "EN_PREPARACION", "LISTO"));
    }

    @Override
    public Pedido guardarPedido(Pedido pedido) {
        PedidoEntity pedidoEntity = pedidoEntityMapper.toEntity(pedido);

        for (DetallePedidoEntity detalle : pedidoEntity.getDetalles()) {
            detalle.setPedido(pedidoEntity);
        }

        return pedidoEntityMapper.toPedido(pedidoRepository.save(pedidoEntity));
    }

    @Override
    public List<Pedido> listarPedidos(String estado, int pagina, int tamanio, Long restauranteId) {
        Pageable pageable = PageRequest.of(pagina,tamanio);
        Page<PedidoEntity> pedidoEntities = pedidoRepository.findByEstadoAndRestaurante_Id(estado, restauranteId, pageable);

        if (pagina >= pedidoEntities.getTotalPages()) {
            throw new NoDataFoundException("La página solicitada no existe");
        }

        System.out.println("cantidad: "+pedidoEntities.get().count());
        if(pedidoEntities.isEmpty()){
            throw new NoDataFoundException("No hay pedidos para mostrar1");
        }

        return pedidoEntities.getContent().stream()
                .map(pedidoEntityMapper::toPedido).collect(Collectors.toList());

    }
}
