package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IUsuarioSesionServicePort;
import com.pragma.powerup.domain.exception.PedidoEnProcesoException;
import com.pragma.powerup.domain.exception.PedidoInvalidoException;
import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.domain.model.PedidoDetalle;
import com.pragma.powerup.domain.model.UsuarioRestaurante;
import com.pragma.powerup.domain.spi.IPedidoPersistencePort;
import com.pragma.powerup.domain.spi.IPlatoPersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantePersistencePort;
import com.pragma.powerup.domain.spi.IUsuarioRestaurantePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static com.pragma.powerup.domain.util.EstadoConstants.ESTADO_PENDIENTE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PedidoUseCaseTest {

    @Mock
    private IPedidoPersistencePort pedidoPersistencePort;

    @Mock
    private IUsuarioSesionServicePort usuarioSesionServicePort;

    @Mock
    private IPlatoPersistencePort platoPersistencePort;

    @InjectMocks
    private PedidoUseCase pedidoUseCase;


    @Mock
    private IUsuarioRestaurantePersistencePort usuarioRestaurantePersistencePort;


    @Test
    void crearPedido_Correcto_SeteaEstadoPendienteYGuarda() {
        // Arrange
        Long clienteId = 1L;
        Long restauranteId = 100L;
        Long platoId = 10L;

        Pedido pedido = new Pedido();
        pedido.setRestauranteId(restauranteId);

        PedidoDetalle pedidoDetalle = new PedidoDetalle();
        pedidoDetalle.setCantidad(3);
        pedidoDetalle.setPlatoId(platoId);
        pedido.setDetalles(List.of(pedidoDetalle));

        when(usuarioSesionServicePort.obtenerIdUsuarioAutenticado()).thenReturn(clienteId);
        when(pedidoPersistencePort.existePedidoEnProcesoParaElCliente(clienteId)).thenReturn(false);
        when(platoPersistencePort.todosPertenecenARestaurante(restauranteId, pedido.getDetalles())).thenReturn(true);
        when(pedidoPersistencePort.guardarPedido(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Pedido resultado = pedidoUseCase.crearPedido(pedido);

        // Assert
        assertEquals("PENDIENTE", resultado.getEstado());
        assertEquals(clienteId, resultado.getClienteId());

        verify(pedidoPersistencePort).guardarPedido(pedido);
    }



    @Test
    void crearPedido_ClienteConPedidoEnProceso_LanzaExcepcion() {
        Long clienteId = 1L;
        Long platoId = 10L;

        PedidoDetalle pedidoDetalle = new PedidoDetalle();
        pedidoDetalle.setCantidad(3);
        pedidoDetalle.setPlatoId(platoId);


        Pedido pedido = new Pedido();
        pedido.setRestauranteId(100L);
        pedido.setDetalles(List.of(pedidoDetalle));

        when(usuarioSesionServicePort.obtenerIdUsuarioAutenticado()).thenReturn(clienteId);
        when(pedidoPersistencePort.existePedidoEnProcesoParaElCliente(clienteId)).thenReturn(true);

        assertThrows(PedidoEnProcesoException.class, () -> pedidoUseCase.crearPedido(pedido));

        verify(pedidoPersistencePort, never()).guardarPedido(any());
    }


    @Test
    void crearPedido_SinDetalles_LanzaExcepcion() {
        Long clienteId = 1L;
        Pedido pedido = new Pedido();
        pedido.setDetalles(Collections.emptyList());

        when(usuarioSesionServicePort.obtenerIdUsuarioAutenticado()).thenReturn(clienteId);
        when(pedidoPersistencePort.existePedidoEnProcesoParaElCliente(clienteId)).thenReturn(false);

        assertThrows(PedidoInvalidoException.class, () -> pedidoUseCase.crearPedido(pedido));
    }


    @Test
    void crearPedido_PlatosDeDistintosRestaurantes_LanzaExcepcion() {
        Long clienteId = 1L;
        Long restauranteId = 100L;
        Long platoId = 10L;

        PedidoDetalle pedidoDetalle = new PedidoDetalle();
        pedidoDetalle.setCantidad(3);
        pedidoDetalle.setPlatoId(platoId);

        Pedido pedido = new Pedido();
        pedido.setRestauranteId(restauranteId);
        pedido.setDetalles(List.of(pedidoDetalle));

        when(usuarioSesionServicePort.obtenerIdUsuarioAutenticado()).thenReturn(clienteId);
        when(pedidoPersistencePort.existePedidoEnProcesoParaElCliente(clienteId)).thenReturn(false);
        when(platoPersistencePort.todosPertenecenARestaurante(restauranteId, pedido.getDetalles())).thenReturn(false);

        assertThrows(PedidoInvalidoException.class, () -> pedidoUseCase.crearPedido(pedido));
    }


    @Test
    void listarPedidos_FiltradoPorEstadoYRestaurante_DevuelveLista() {
        // Arrange
        String estado = ESTADO_PENDIENTE;
        int pagina = 0;
        int tamanio = 5;
        Long empleadoId = 1L;
        Long restauranteId = 10L;
        Long clienteId = 100L;

        Pedido pedido1 = new Pedido  ( 1L, clienteId, restauranteId,estado, List.of());
        Pedido pedido2 = new Pedido(2L, clienteId, restauranteId, estado, List.of());
        List<Pedido> pedidos = List.of(pedido1, pedido2);

        when(usuarioSesionServicePort.obtenerIdUsuarioAutenticado()).thenReturn(empleadoId);
        when(usuarioRestaurantePersistencePort.getRestauranteEmpleo(empleadoId))
                .thenReturn(new UsuarioRestaurante(1L,empleadoId, restauranteId));
        when(pedidoPersistencePort.listarPedidos(estado, pagina, tamanio, restauranteId)).thenReturn(pedidos);

        // Act
        List<Pedido> resultado = pedidoUseCase.listarPedidos(estado, pagina, tamanio);

        // Assert
        assertEquals(2, resultado.size());
        assertEquals(estado, resultado.get(0).getEstado());
        assertEquals(restauranteId, resultado.get(0).getRestauranteId());

        verify(pedidoPersistencePort).listarPedidos(estado, pagina, tamanio, restauranteId);
    }


}