package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IPedidoServicePort;
import com.pragma.powerup.domain.api.IUsuarioSesionServicePort;
import com.pragma.powerup.domain.exception.PedidoEnProcesoException;
import com.pragma.powerup.domain.exception.PedidoInvalidoException;
import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.domain.spi.IPedidoPersistencePort;
import com.pragma.powerup.domain.spi.IPlatoPersistencePort;
import com.pragma.powerup.domain.spi.IUsuarioRestaurantePersistencePort;

import java.util.List;

import static com.pragma.powerup.domain.util.EstadoConstants.ESTADO_PENDIENTE;
import static com.pragma.powerup.domain.util.ExceptionMessageConstants.*;


public class PedidoUseCase implements IPedidoServicePort {


    private final IPedidoPersistencePort pedidoPersistencePort;
    private final IPlatoPersistencePort platoPersistencePort;
    private final IUsuarioSesionServicePort usuarioSesionServicePort;
    private final IUsuarioRestaurantePersistencePort usuarioRestaurantePersistencePort;

    public PedidoUseCase(IPedidoPersistencePort pedidoPersistencePort, IPlatoPersistencePort platoPersistencePort, IUsuarioSesionServicePort usuarioSesionServicePort, IUsuarioRestaurantePersistencePort usuarioRestaurantePersistencePort) {
        this.pedidoPersistencePort = pedidoPersistencePort;
        this.platoPersistencePort = platoPersistencePort;
        this.usuarioSesionServicePort = usuarioSesionServicePort;
        this.usuarioRestaurantePersistencePort = usuarioRestaurantePersistencePort;
    }



    @Override
    public Pedido crearPedido(Pedido pedido) {

        Long idUsuarioAutentiicado = usuarioSesionServicePort.obtenerIdUsuarioAutenticado();

        boolean tienePedidoEnProceso = pedidoPersistencePort.existePedidoEnProcesoParaElCliente(idUsuarioAutentiicado);

        if (tienePedidoEnProceso) {
            throw new PedidoEnProcesoException(PEDIDO_EN_PROGRESO);
        }

        if(pedido.getDetalles().size()==0){
            throw new PedidoInvalidoException(MIN_CANT_PLATOS);
        }

        if (!platoPersistencePort.todosPertenecenARestaurante(pedido.getRestauranteId(), pedido.getDetalles())) {
            throw new PedidoInvalidoException(PLATOS_DIFERENTE_RESTAURANTE);
        }

        pedido.setClienteId(idUsuarioAutentiicado);
        pedido.setEstado(ESTADO_PENDIENTE);
        return pedidoPersistencePort.guardarPedido(pedido);

    }

    @Override
    public List<Pedido> listarPedidos(String estado, int pagina, int tamanio) {

        Long IdUsuarioAutenticado = usuarioSesionServicePort.obtenerIdUsuarioAutenticado();

        Long restauranteId = usuarioRestaurantePersistencePort.getRestauranteEmpleo(IdUsuarioAutenticado).getRestauranteId();

        return this.pedidoPersistencePort.listarPedidos(estado,pagina,tamanio,restauranteId);

    }

}
