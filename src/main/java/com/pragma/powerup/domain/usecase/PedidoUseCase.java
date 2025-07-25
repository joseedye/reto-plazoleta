package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IPedidoServicePort;
import com.pragma.powerup.domain.api.IUsuarioSesionServicePort;
import com.pragma.powerup.domain.exception.PedidoEnProcesoException;
import com.pragma.powerup.domain.exception.PedidoInvalidoException;
import com.pragma.powerup.domain.exception.UsuarioSinPermisoException;
import com.pragma.powerup.domain.model.*;
import com.pragma.powerup.domain.spi.*;
import com.pragma.powerup.domain.util.EstadoPedido;

import java.util.Random;

import static com.pragma.powerup.domain.util.ExceptionMessageConstants.*;
import static com.pragma.powerup.domain.util.ValidacionConstants.MSG_LISTO;
import static com.pragma.powerup.domain.util.ValidacionConstants.TAMANIO_PIN;


public class PedidoUseCase implements IPedidoServicePort {


    private final IPedidoPersistencePort pedidoPersistencePort;
    private final IPlatoPersistencePort platoPersistencePort;
    private final IUsuarioSesionServicePort usuarioSesionServicePort;
    private final IUsuarioRestaurantePersistencePort usuarioRestaurantePersistencePort;
    private final IUsuarioPersistencePort usuarioPersistencePort;
    private final INotificacionSmsPort notificacionSmsPort;

    public PedidoUseCase(IPedidoPersistencePort pedidoPersistencePort, IPlatoPersistencePort platoPersistencePort, IUsuarioSesionServicePort usuarioSesionServicePort, IUsuarioRestaurantePersistencePort usuarioRestaurantePersistencePort, IUsuarioPersistencePort usuarioPersistencePort, INotificacionSmsPort notificacionSmsPort) {
        this.pedidoPersistencePort = pedidoPersistencePort;
        this.platoPersistencePort = platoPersistencePort;
        this.usuarioSesionServicePort = usuarioSesionServicePort;
        this.usuarioRestaurantePersistencePort = usuarioRestaurantePersistencePort;
        this.usuarioPersistencePort = usuarioPersistencePort;
        this.notificacionSmsPort = notificacionSmsPort;
    }



    @Override
    public Pedido crearPedido(Pedido pedido) {

        Long idUsuarioAutentiicado = usuarioSesionServicePort.obtenerIdUsuarioAutenticado();

        boolean tienePedidoEnProceso = pedidoPersistencePort.existePedidoEnProcesoParaElCliente(idUsuarioAutentiicado);

        if (tienePedidoEnProceso) {
            throw new PedidoEnProcesoException(PEDIDO_EN_PROGRESO);
        }

        if(pedido.getDetalles().isEmpty()){
            throw new PedidoInvalidoException(MIN_CANT_PLATOS);
        }

        if (!platoPersistencePort.todosPertenecenARestaurante(pedido.getRestauranteId(), pedido.getDetalles())) {
            throw new PedidoInvalidoException(PLATOS_DIFERENTE_RESTAURANTE);
        }

        pedido.setClienteId(idUsuarioAutentiicado);
        pedido.setEstado(EstadoPedido.PENDIENTE.toString());
        return pedidoPersistencePort.guardarPedido(pedido);

    }

    @Override
    public GenericoPaginadoOut<Pedido> listarPedidos(PedidoPaginado pedidoPaginado) {

        Long IdUsuarioAutenticado = usuarioSesionServicePort.obtenerIdUsuarioAutenticado();

        Long restauranteId = usuarioRestaurantePersistencePort.getRestauranteEmpleo(IdUsuarioAutenticado).getRestauranteId();

        return this.pedidoPersistencePort.listarPedidos(pedidoPaginado,restauranteId);

    }

    @Override
    public Pedido asignarPedido(Long pedidoId) {

        Long idEmpleado = usuarioSesionServicePort.obtenerIdUsuarioAutenticado();
        Usuario empleado = usuarioPersistencePort.getUsuario(idEmpleado);
        Long restauranteId = usuarioRestaurantePersistencePort.getRestauranteEmpleo(idEmpleado).getRestauranteId();

        Pedido pedido = pedidoPersistencePort.getPedido(pedidoId);

        if (!pedido.getRestauranteId().equals(restauranteId)) {
            throw new UsuarioSinPermisoException(MODIFICA_PLATO_NO_PERMITIDA);
        }

        pedido.setEmpleadoAsignado(empleado);
        pedido.setEstado(EstadoPedido.EN_PREPARACION.toString());

        return pedidoPersistencePort.actualizarPedido(pedido);

    }

    @Override
    public Pedido marcarPedidoComoListo(Long pedidoId) {

        Pedido pedido = pedidoPersistencePort.getPedido(pedidoId);
        pedido.setEstado(EstadoPedido.LISTO.toString());
        Long pinGenerado = Long.valueOf(generarPinAleatorio(TAMANIO_PIN));
        pedido.setPinSeguridad(pinGenerado);
        System.out.println("pin luego de asignado"+pedido.getPinSeguridad());
        Pedido pedidoActualizado = pedidoPersistencePort.actualizarPedido(pedido);


        Long clienteId = pedido.getClienteId();
        Usuario cliente = usuarioPersistencePort.getUsuario(clienteId);
        String telefono  = cliente.getCelular();

        String mensaje = MSG_LISTO + pedido.getPinSeguridad();
        notificacionSmsPort.enviarSms(telefono, mensaje);
        return pedidoActualizado;
    }

    private int generarPinAleatorio(int length) {
        Random random = new Random();
        int min = (int) Math.pow(10, length - 1);
        int max = (int) Math.pow(10, length) - 1;
        return random.nextInt(max - min + 1) + min;
    }

}
