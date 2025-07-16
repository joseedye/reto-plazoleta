package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IUsuarioSesionServicePort;
import com.pragma.powerup.domain.exception.RestauranteNoPermitidoException;
import com.pragma.powerup.domain.exception.UsuarioSinPermisoException;
import com.pragma.powerup.domain.model.*;
import com.pragma.powerup.domain.spi.IPlatoPersistencePort;
import com.pragma.powerup.domain.spi.IUsuarioPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.pragma.powerup.domain.util.RolConstants.ROL_EMPLEADO;
import static com.pragma.powerup.domain.util.RolConstants.ROL_PROPIETARIO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlatoUseCaseTest {

    @InjectMocks
    private PlatoUseCase platoUseCase;

    @Mock
    private IUsuarioSesionServicePort usuarioSesionServicePort;

    @Mock
    private IUsuarioPersistencePort usuarioPersistencePort;

    @Mock
    private IPlatoPersistencePort platoPersistencePort;

    @Test
    void testSavePlato_Exito() {
        // Arrange
        Long idUsuario = 10L;
        when(usuarioSesionServicePort.obtenerIdUsuarioAutenticado()).thenReturn(idUsuario);

        Usuario propietario = new Usuario();
        propietario.setId(idUsuario);
        propietario.setRol(new Rol(ROL_PROPIETARIO, "PROPIETARIO"));

        when(usuarioPersistencePort.getUsuario(idUsuario)).thenReturn(propietario);

        Restaurante restaurante = new Restaurante();
        restaurante.setPropietario(propietario);

        Plato plato = new Plato();
        plato.setNombre("Bandeja Paisa");
        plato.setPrecio(25000L);
        plato.setDescripcion("Delicioso plato típico");
        plato.setUrlImagen("http://img.com/bandeja.jpg");
        plato.setCategoria(new Categoria(1L,"Ensaladas"));
        plato.setRestaurante(restaurante);


        when(platoPersistencePort.savePlato(any(Plato.class))).thenReturn(plato);

        // Act
        Plato resultado = platoUseCase.savePlato(plato);

        // Assert
        assertNotNull(resultado);
        assertEquals("Bandeja Paisa", resultado.getNombre());
        assertTrue(resultado.isEstado());
        verify(usuarioSesionServicePort).obtenerIdUsuarioAutenticado();
        verify(usuarioPersistencePort).getUsuario(idUsuario);
        verify(platoPersistencePort).savePlato(any(Plato.class));
    }

    @Test
    void testSavePlato_NoEsPropietario_LanzaUsuarioSinPermisoException() {
        // Arrange
        Long idUsuario = 11L;
        when(usuarioSesionServicePort.obtenerIdUsuarioAutenticado()).thenReturn(idUsuario);

        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);
        usuario.setRol(new Rol(1L, "ADMINISTRADOR")); // No es propietario

        when(usuarioPersistencePort.getUsuario(idUsuario)).thenReturn(usuario);

        Plato plato = new Plato();
        plato.setRestaurante(new Restaurante()); // No importa en este test

        // Act & Assert
        assertThrows(UsuarioSinPermisoException.class, () -> {
            platoUseCase.savePlato(plato);
        });

        verify(usuarioSesionServicePort).obtenerIdUsuarioAutenticado();
        verify(usuarioPersistencePort).getUsuario(idUsuario);
        verifyNoInteractions(platoPersistencePort);
    }

    @Test
    void testSavePlato_UsuarioNoPropietarioDelRestaurante_LanzaRestauranteNoPermitidoException() {
        // Arrange
        Long idUsuario = 12L;
        when(usuarioSesionServicePort.obtenerIdUsuarioAutenticado()).thenReturn(idUsuario);

        Usuario propietario = new Usuario();
        propietario.setId(idUsuario);
        propietario.setRol(new Rol(ROL_PROPIETARIO, "PROPIETARIO"));

        Usuario otroPropietario = new Usuario();
        otroPropietario.setId(99L);
        otroPropietario.setRol(new Rol(ROL_PROPIETARIO, "PROPIETARIO"));

        when(usuarioPersistencePort.getUsuario(idUsuario)).thenReturn(propietario);

        Restaurante restaurante = new Restaurante();
        restaurante.setPropietario(otroPropietario); // Restaurante pertenece a otro propietario

        Plato plato = new Plato();
        plato.setRestaurante(restaurante);

        // Act & Assert
        assertThrows(RestauranteNoPermitidoException.class, () -> {
            platoUseCase.savePlato(plato);
        });

        verify(usuarioSesionServicePort).obtenerIdUsuarioAutenticado();
        verify(usuarioPersistencePort).getUsuario(idUsuario);
        verifyNoInteractions(platoPersistencePort);
    }





    @Test
    void testUpdatePlatoSoloPropietarioYCamposPermitidos() {

        // Datos de entrada para la actualización
        Plato platoInput = new Plato();
        platoInput.setId(10L);
        platoInput.setDescripcion("Nueva descripción");
        platoInput.setPrecio(15000L);
        platoInput.setNombre("nuevo nombre");

        // Plato existente
        Plato platoExistente = new Plato();
        platoExistente.setId(10L);
        platoExistente.setDescripcion("Descripcion existente");
        platoExistente.setPrecio(10000L);
        platoExistente.setNombre("nombre existente");

        // Usuario propietario autenticado
        Usuario propietario = new Usuario();
        propietario.setId(1L);
        propietario.setNombres("Juan");
        propietario.setApellidos("Perez");
        propietario.setRol(new Rol(ROL_PROPIETARIO,"PROPIETARIO"));

        // Restaurante con propietario
        Restaurante restaurante = new Restaurante();
        restaurante.setPropietario(propietario);

        // El plato está asociado al restaurante del propietario
        platoExistente.setRestaurante(restaurante);

        // Mocks
        when(usuarioSesionServicePort.obtenerIdUsuarioAutenticado()).thenReturn(propietario.getId());
        when(usuarioPersistencePort.getUsuario(propietario.getId())).thenReturn(propietario);
        when(platoPersistencePort.getPlato(platoInput.getId())).thenReturn(platoExistente);
        when(platoPersistencePort.updatePlato(any(Plato.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Ejecutar
        Plato platoActualizado = platoUseCase.updatePlato(platoInput);

        // Verificaciones
        assertNotNull(platoActualizado);
        assertEquals(platoInput.getDescripcion(), platoActualizado.getDescripcion());
        assertEquals(platoInput.getPrecio(), platoActualizado.getPrecio());
        assertNotEquals(platoInput.getNombre(),platoActualizado.getNombre());
        assertEquals(platoActualizado.getNombre(),"nombre existente");


        verify(platoPersistencePort).updatePlato(platoExistente);
    }




    @Test
    void testHabilitarPlato() {

        // Datos de entrada para la actualización
        Plato platoInput = new Plato();
        platoInput.setId(10L);
        platoInput.setEstado(false);


        // Usuario propietario autenticado
        Usuario propietario = new Usuario();
        propietario.setId(1L);
        propietario.setNombres("Juan");
        propietario.setApellidos("Perez");
        propietario.setRol(new Rol(ROL_PROPIETARIO,"PROPIETARIO"));

        // Restaurante con propietario
        Restaurante restaurante = new Restaurante();
        restaurante.setPropietario(propietario);

        platoInput.setRestaurante(restaurante);

        // Mocks
        when(platoPersistencePort.getPlato(platoInput.getId())).thenReturn(platoInput);
        when(usuarioSesionServicePort.obtenerIdUsuarioAutenticado()).thenReturn(propietario.getId());
        when(platoPersistencePort.updatePlato(platoInput)).thenReturn(platoInput);

        // Ejecutar
        Plato platoActualizado = platoUseCase.habilitarPlato(platoInput.getId());

        // Verificaciones
        assertNotNull(platoActualizado);
        assertEquals(platoActualizado.isEstado(),true);
        verify(platoPersistencePort).updatePlato(platoActualizado);
    }


    @Test
    void testDeshabilitarPlato() {

        // Datos de entrada para la actualización
        Plato platoInput = new Plato();
        platoInput.setId(10L);
        platoInput.setEstado(true);


        // Usuario propietario autenticado
        Usuario propietario = new Usuario();
        propietario.setId(1L);
        propietario.setNombres("Juan");
        propietario.setApellidos("Perez");
        propietario.setRol(new Rol(ROL_PROPIETARIO,"PROPIETARIO"));

        // Restaurante con propietario
        Restaurante restaurante = new Restaurante();
        restaurante.setPropietario(propietario);

        platoInput.setRestaurante(restaurante);

        // Mocks
        when(platoPersistencePort.getPlato(platoInput.getId())).thenReturn(platoInput);
        when(usuarioSesionServicePort.obtenerIdUsuarioAutenticado()).thenReturn(propietario.getId());
        when(platoPersistencePort.updatePlato(platoInput)).thenReturn(platoInput);

        // Ejecutar
        Plato platoActualizado = platoUseCase.deshabilitarPlato(platoInput.getId());

        // Verificaciones
        assertNotNull(platoActualizado);
        assertEquals(platoActualizado.isEstado(),false);
        verify(platoPersistencePort).updatePlato(platoActualizado);
    }




    @Test
    void testHabilitarPlatoPropietarioDistintoLanzaException() {

        // Datos de entrada para la actualización
        Plato platoInput = new Plato();
        platoInput.setId(10L);
        platoInput.setEstado(false);


        // Usuario propietario autenticado
        Usuario propietario = new Usuario();
        propietario.setId(1L);
        propietario.setNombres("Juan");
        propietario.setApellidos("Perez");
        propietario.setRol(new Rol(ROL_PROPIETARIO,"PROPIETARIO"));

        Usuario otroPropietario = new Usuario();
        otroPropietario.setId(2L);

        // Restaurante con propietario
        Restaurante restaurante = new Restaurante();
        restaurante.setPropietario(propietario);

        platoInput.setRestaurante(restaurante);

        // Mocks
        when(platoPersistencePort.getPlato(platoInput.getId())).thenReturn(platoInput);
        when(usuarioSesionServicePort.obtenerIdUsuarioAutenticado()).thenReturn(otroPropietario.getId());

        // Ejecutar
        Exception exception = assertThrows(UsuarioSinPermisoException.class, () -> {
            platoUseCase.habilitarPlato(platoInput.getId());
        });


    }



    @Test
    void testListarPlatosPorCategoriaYPaginacion() {
        // Arrange
        int pagina = 0;
        int tamanio = 2;
        Long categoriaId = 1L;
        Categoria cat = new Categoria(categoriaId,"nueva");

        Plato plato1 = new Plato();
        plato1.setNombre("Bandeja Paisa");
        plato1.setCategoria(cat);

        Plato plato2 = new Plato();
        plato2.setNombre("Ajiaco");
        plato2.setCategoria(cat);

        List<Plato> platosEsperados = List.of(plato1, plato2);

        when(platoPersistencePort.listarPlatos(pagina, tamanio, categoriaId)).thenReturn(platosEsperados);

        // Act
        List<Plato> resultado = platoUseCase.listarPlatos(pagina, tamanio, categoriaId);

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("Bandeja Paisa", resultado.get(0).getNombre());
        assertEquals(cat, resultado.get(0).getCategoria());
        assertEquals("Ajiaco", resultado.get(1).getNombre());

        verify(platoPersistencePort).listarPlatos(pagina, tamanio, categoriaId);
    }




}