package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.GenericoPaginadoOut;
import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.domain.model.PedidoPaginado;
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

import static com.pragma.powerup.infrastructure.util.ExcepcionMessageConstants.*;

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
    public GenericoPaginadoOut<Pedido> listarPedidos(PedidoPaginado pedidoPaginado, Long restauranteId) {

        Pageable pageable = PageRequest.of(pedidoPaginado.getPagina(),pedidoPaginado.getTamanio());
        Page<PedidoEntity> pedidoEntities = pedidoRepository.findByEstadoAndRestaurante_Id(pedidoPaginado.getEstado(), restauranteId, pageable);

        if (pedidoPaginado.getPagina() >= pedidoEntities.getTotalPages()) {
            throw new NoDataFoundException(PAGINA_NO_EXISTE);
        }

        if(pedidoEntities.isEmpty()){
            throw new NoDataFoundException(NO_HAY_PEDIDOS);
        }


        List<Pedido> pedidos  = pedidoEntities.getContent()
                .stream()
                .map(pedidoEntityMapper::toPedido)
                .collect(Collectors.toList());

        GenericoPaginadoOut <Pedido> pedidoPaginadoOut = new GenericoPaginadoOut <Pedido>();
        pedidoPaginadoOut.setLista(pedidos);
        pedidoPaginadoOut.setPaginaActual(pedidoEntities.getNumber());
        pedidoPaginadoOut.setTotalPaginas(pedidoEntities.getTotalPages());
        pedidoPaginadoOut.setTotalElementos(pedidoEntities.getTotalElements());
        pedidoPaginadoOut.setTamanioPagina(pedidoEntities.getSize());

        return pedidoPaginadoOut;

    }

    @Override
    public Pedido getPedido(Long idPedido) {
        PedidoEntity pedidoEntity = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new NoDataFoundException(NO_DATA));
        return pedidoEntityMapper.toPedido(pedidoEntity);
    }

    @Override
    public Pedido actualizarPedido(Pedido pedido) {
        System.out.println("antes del mapper"+pedido.getPinSeguridad());
        PedidoEntity pedidoEntity = pedidoEntityMapper.toEntity(pedido);
        System.out.println("luego del mapper"+pedidoEntity.getPinSeguridad());
        for (DetallePedidoEntity detalle : pedidoEntity.getDetalles()) {
            detalle.setPedido(pedidoEntity);
        }
        PedidoEntity pedidoGuardado = pedidoRepository.save(pedidoEntity);
        return pedidoEntityMapper.toPedido(pedidoGuardado);
    }
}
