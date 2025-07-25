package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IUsuarioSesionServicePort;
import com.pragma.powerup.domain.exception.RolNoPermitidoException;
import com.pragma.powerup.domain.exception.UsuarioNotFoundException;
import com.pragma.powerup.domain.model.*;
import com.pragma.powerup.domain.spi.IRestaurantePersistencePort;
import com.pragma.powerup.domain.spi.IUsuarioPersistencePort;
import com.pragma.powerup.domain.util.RolEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestauranteUseCaseTest {

    @InjectMocks
    private RestauranteUseCase restauranteUseCase;

    @Mock
    private IRestaurantePersistencePort restaurantePersistencePort;

    @Mock
    private IUsuarioPersistencePort usuarioPersistencePort;

    @Mock
    private IUsuarioSesionServicePort usuarioSesionServicePort;


    @Test
    void testSaveRestaurante_Exito() {
        // Arrange
        Restaurante restaurante = new Restaurante();
        restaurante.setNombre("Restaurante El Buen Sabor");
        restaurante.setNIT("123456789");
        restaurante.setDireccion("Cra 1 #23-45");
        restaurante.setTelefono("+573001112233");
        restaurante.setUrlLogo("http://logo.com/logo.jpg");

        Usuario propietario = new Usuario();
        propietario.setId(1L);

        restaurante.setPropietario(propietario);

        // Usuario con rol PROPIETARIO
        Rol rolPropietario = new Rol(RolEnum.PROPIETARIO.getId(), "PROPIETARIO");
        propietario.setRol(rolPropietario);

        //usuario sesion
        Long usuarioSesion = 144L;
        Usuario administrador = new Usuario();
        administrador.setId(usuarioSesion);
        administrador.setRol(new Rol(RolEnum.ADMIN.getId(),"ADMINISTRADOR"));

        // Mocks
        when(usuarioSesionServicePort.obtenerIdUsuarioAutenticado()).thenReturn(usuarioSesion);
        when(usuarioPersistencePort.getUsuario(usuarioSesion)).thenReturn(administrador);
        when(usuarioPersistencePort.getUsuario(1L)).thenReturn(propietario);
        when(restaurantePersistencePort.saveRestaurante(any(Restaurante.class))).thenReturn(restaurante);

        // Act
        Restaurante resultado = restauranteUseCase.saveRestaurante(restaurante);

        // Assert
        assertNotNull(resultado);
        assertEquals("Restaurante El Buen Sabor", resultado.getNombre());
        verify(usuarioPersistencePort).getUsuario(1L);
        verify(restaurantePersistencePort).saveRestaurante(any());
    }

    @Test
    void testSaveRestaurante_UsuarioNoExiste_LanzaExcepcion() {
        // Arrange
        Restaurante restaurante = new Restaurante();
        Usuario propietario = new Usuario();
        propietario.setId(99L);
        restaurante.setPropietario(propietario);

        //usuario sesion
        Long usuarioSesion = 144L;
        Usuario administrador = new Usuario();
        administrador.setId(usuarioSesion);
        administrador.setRol(new Rol(RolEnum.ADMIN.getId(),"ADMINISTRADOR"));

        // Mocks
        when(usuarioSesionServicePort.obtenerIdUsuarioAutenticado()).thenReturn(usuarioSesion);
        when(usuarioPersistencePort.getUsuario(usuarioSesion)).thenReturn(administrador);

        when(usuarioPersistencePort.getUsuario(99L)).thenReturn(null);

        // Act & Assert
        assertThrows(UsuarioNotFoundException.class, () -> {
            restauranteUseCase.saveRestaurante(restaurante);
        });

        verify(usuarioPersistencePort).getUsuario(99L);
        verify(restaurantePersistencePort, never()).saveRestaurante(any());
    }

    @Test
    void testSaveRestaurante_UsuarioNoEsPropietario_LanzaExcepcion() {
        // Arrange
        Restaurante restaurante = new Restaurante();
        Usuario propietario = new Usuario();
        propietario.setId(5L);
        restaurante.setPropietario(propietario);

        // Usuario con rol EMPLEADO
        Rol rolAdmin = new Rol(RolEnum.EMPLEADO.getId(), "EMPLEADO");
        propietario.setRol(rolAdmin);

        //usuario sesion
        Long usuarioSesion = 144L;
        Usuario administrador = new Usuario();
        administrador.setId(usuarioSesion);
        administrador.setRol(new Rol(RolEnum.ADMIN.getId(),"ADMINISTRADOR"));

        // Mocks
        when(usuarioSesionServicePort.obtenerIdUsuarioAutenticado()).thenReturn(usuarioSesion);
        when(usuarioPersistencePort.getUsuario(usuarioSesion)).thenReturn(administrador);
        when(usuarioPersistencePort.getUsuario(5L)).thenReturn(propietario);

        // Act & Assert
        assertThrows(RolNoPermitidoException.class, () -> {
            restauranteUseCase.saveRestaurante(restaurante);
        });

        verify(usuarioPersistencePort).getUsuario(5L);
        verify(restaurantePersistencePort, never()).saveRestaurante(any());
    }

    @Test
    void testSaveRestaurante_UsuarioNoEsAdministrador_LanzaExcepcion() {
        // Arrange
        Restaurante restaurante = new Restaurante();

        //usuario sesion
        Long usuarioSesion = 144L;
        Usuario administrador = new Usuario();
        administrador.setId(usuarioSesion);
        administrador.setRol(new Rol(RolEnum.PROPIETARIO.getId(),"EMPLEADO"));

        // Mocks
        when(usuarioSesionServicePort.obtenerIdUsuarioAutenticado()).thenReturn(usuarioSesion);
        when(usuarioPersistencePort.getUsuario(usuarioSesion)).thenReturn(administrador);

        // Act & Assert
        assertThrows(RolNoPermitidoException.class, () -> {
            restauranteUseCase.saveRestaurante(restaurante);
        });

        verify(usuarioPersistencePort).getUsuario(usuarioSesion);
        verify(restaurantePersistencePort, never()).saveRestaurante(any());
    }



    @Test
    void testListarRestaurantesConPaginacionDevuelveSoloNombreYLogo() {
        // Arrange
        int pagina = 0;
        int tamanio = 2;

        Restaurante r1 = new Restaurante();
        r1.setNombre("Arepa House");
        r1.setUrlLogo("http://abc.com");
        r1.setDireccion("No DIR");

        Restaurante r2 = new Restaurante();
        r2.setNombre("Burger Plaza");
        r2.setUrlLogo("http://xyz.com");
        r2.setTelefono("dos dir");

        List<Restaurante> listaEsperada = List.of(r1, r2);

        GenericoPaginadoOut <Restaurante> genericoPaginadoOut = new GenericoPaginadoOut<Restaurante> ();
        genericoPaginadoOut.setLista(listaEsperada);

        RestaurantePaginado restaurantePaginado = new RestaurantePaginado(pagina, tamanio);
        when(restaurantePersistencePort.listarRestaurantes(restaurantePaginado)).thenReturn(genericoPaginadoOut);

        // Act
        GenericoPaginadoOut<Restaurante> resultado = restauranteUseCase.listarRestaurantes(restaurantePaginado);


        // Assert
        assertEquals(2, resultado.getLista().size());

        assertEquals("Arepa House", resultado.getLista().get(0).getNombre());
        assertEquals("http://abc.com", resultado.getLista().get(0).getUrlLogo());

        assertEquals("Burger Plaza", resultado.getLista().get(1).getNombre());
        assertEquals("http://xyz.com", resultado.getLista().get(1).getUrlLogo());

        // Verificar que solo se llamó una vez al persistence port
        verify(restaurantePersistencePort, times(1)).listarRestaurantes(restaurantePaginado);




    }





}