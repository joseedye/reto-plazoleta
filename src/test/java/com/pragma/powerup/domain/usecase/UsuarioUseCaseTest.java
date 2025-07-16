package com.pragma.powerup.domain.usecase;


import com.pragma.powerup.domain.api.IEncriptarContrasenaServicePort;
import com.pragma.powerup.domain.api.IUsuarioSesionServicePort;
import com.pragma.powerup.domain.exception.CorreoYaRegistradoException;
import com.pragma.powerup.domain.exception.NoDataFoundException;
import com.pragma.powerup.domain.exception.UsuarioSinMayoriaEdadException;
import com.pragma.powerup.domain.exception.UsuarioSinPermisoException;
import com.pragma.powerup.domain.model.Restaurante;
import com.pragma.powerup.domain.model.Rol;
import com.pragma.powerup.domain.model.Usuario;
import com.pragma.powerup.domain.spi.IRestaurantePersistencePort;
import com.pragma.powerup.domain.spi.IRolPersistencePort;
import com.pragma.powerup.domain.spi.IUsuarioPersistencePort;
import com.pragma.powerup.domain.spi.IUsuarioRestaurantePersistencePort;
import com.pragma.powerup.infrastructure.exception.RolNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static com.pragma.powerup.domain.util.ExceptionMessageConstants.USUARIO_NO_MAYOR_EDAD;
import static com.pragma.powerup.domain.util.RolConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioUseCaseTest {

    @InjectMocks
    private UsuarioUseCase usuarioUseCase;

    @Mock
    private IUsuarioPersistencePort usuarioPersistencePort;

    @Mock
    private IRolPersistencePort rolPersistencePort;

    @Mock
    private IEncriptarContrasenaServicePort encriptarContrasenaServicePort;

    @Mock
    private IUsuarioRestaurantePersistencePort usuarioRestaurantePersistencePort;

    @Mock
    private IRestaurantePersistencePort restaurantePersistencePort;

    @Mock
    private IUsuarioSesionServicePort usuarioSesionServicePort;


    //HU1-REQ1 .3 todo ok
    @Test
    void testSaveUsuarioConRolPropietario(){

        //propietario a crear
        Usuario usuario = new Usuario();
        usuario.setCorreo("test@correo.com");
        usuario.setDocumentoIdentidad("123456789");
        usuario.setClave("clave123");
        usuario.setNombres("Nombre Propietario");
        usuario.setFechaNacimiento(LocalDate.of(2000,1,1));

        Long idRolPropietario = ROL_PROPIETARIO;
        Rol rol = new Rol(idRolPropietario,"PROPIETARIO");

        Usuario usuarioCreado = new Usuario();
        usuarioCreado.setId(20L);
        usuarioCreado.setRol(rol);

        Long idUsuarioAdministrador = 100L;

        when(usuarioSesionServicePort.obtenerIdUsuarioAutenticado()).thenReturn(idUsuarioAdministrador);
        when(rolPersistencePort.getRol(idRolPropietario)).thenReturn(rol);
        when(usuarioPersistencePort.saveUsuario(any(Usuario.class))).thenReturn(usuarioCreado);


        Usuario rtaUseCase = usuarioUseCase.saveUsuario(usuario, idRolPropietario);


        // Assert
        assertNotNull(rtaUseCase);
        assertEquals(rtaUseCase.getRol().getId(), idRolPropietario);

    }

    @Test
    void testSavePropietarioConValidacionPorRollanzaExcepcion() {

        Usuario usuario = new Usuario();
        usuario.setCorreo("test@correo.com");
        usuario.setDocumentoIdentidad("123456789");
        usuario.setClave("clave123");
        usuario.setNombres("Nombre Joven");
        usuario.setFechaNacimiento(LocalDate.now().minusYears(20));

        Long idRolInexistente = 400L;

        Long idUsuarioAdministrador = 100L;
        when(usuarioSesionServicePort.obtenerIdUsuarioAutenticado()).thenReturn(idUsuarioAdministrador);

        Exception exception = assertThrows(NoDataFoundException.class, () -> {
            usuarioUseCase.saveUsuario(usuario, idRolInexistente);
        });

    }

    @Test
    void testSavePropietarioConFechaNacimientoNoMayorDeEdad_lanzaExcepcion() {

        Usuario usuario = new Usuario();
        usuario.setCorreo("test@correo.com");
        usuario.setDocumentoIdentidad("123456789");
        usuario.setClave("clave123");
        usuario.setNombres("Nombre Joven");
        usuario.setFechaNacimiento(LocalDate.now().minusYears(10));

        Long idRolPropietario = ROL_PROPIETARIO;


        Long idUsuarioAdministrador = 100L;
        when(usuarioSesionServicePort.obtenerIdUsuarioAutenticado()).thenReturn(idUsuarioAdministrador);



        Exception exception = assertThrows(UsuarioSinMayoriaEdadException.class, () -> {
            usuarioUseCase.saveUsuario(usuario, idRolPropietario);
        });


        String expectedMessage = USUARIO_NO_MAYOR_EDAD;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testSaveUsuarioConRolPropietarioCreadoPorAdmin(){

        //propietario a crear
        Usuario usuario = new Usuario();
        usuario.setCorreo("test@correo.com");
        usuario.setDocumentoIdentidad("123456789");
        usuario.setClave("clave123");
        usuario.setNombres("Nombre Propietario");
        usuario.setFechaNacimiento(LocalDate.of(2000,1,1));

        Long idRolPropietario = ROL_PROPIETARIO;
        Rol rol = new Rol(idRolPropietario,"PROPIETARIO");

        Usuario usuarioCreado = new Usuario();
        usuarioCreado.setId(20L);
        usuarioCreado.setRol(rol);

        Long idUsuarioAdministrador = 100L;
        Usuario usuarioAutenticado = new Usuario();
        usuarioAutenticado.setId(idUsuarioAdministrador);
        usuarioAutenticado.setRol(new Rol(ROL_ADMIN,"ADMINISTRADOR"));

        when(usuarioSesionServicePort.obtenerIdUsuarioAutenticado()).thenReturn(idUsuarioAdministrador);
        when(rolPersistencePort.getRol(idRolPropietario)).thenReturn(rol);
        when(usuarioPersistencePort.saveUsuario(any(Usuario.class))).thenReturn(usuarioCreado);
        when(usuarioPersistencePort.getUsuario(idUsuarioAdministrador)).thenReturn(usuarioAutenticado);



        Usuario rtaUseCase = usuarioUseCase.saveUsuario(usuario, idRolPropietario);


        // Assert
        assertNotNull(rtaUseCase);
        assertEquals(rtaUseCase.getRol().getId(), idRolPropietario);

    }

    @Test
    void testSaveUsuarioConRolPropietarioCreadoPorPropietario(){

        //propietario a crear
        Usuario usuario = new Usuario();
        usuario.setCorreo("test@correo.com");
        usuario.setDocumentoIdentidad("123456789");
        usuario.setClave("clave123");
        usuario.setNombres("Nombre Propietario");
        usuario.setFechaNacimiento(LocalDate.of(2000,1,1));

        Long idRolPropietario = ROL_PROPIETARIO;

        Long idUsuarioSesion = 100L;
        Usuario usuarioAutenticado = new Usuario();
        usuarioAutenticado.setId(idUsuarioSesion);
        usuarioAutenticado.setRol(new Rol(ROL_PROPIETARIO,"PROPIETARIO"));

        when(usuarioSesionServicePort.obtenerIdUsuarioAutenticado()).thenReturn(idUsuarioSesion);
        when(usuarioPersistencePort.getUsuario(idUsuarioSesion)).thenReturn(usuarioAutenticado);

        Exception exception = assertThrows(UsuarioSinPermisoException.class, () -> {
            usuarioUseCase.saveUsuario(usuario, idRolPropietario);
        });

    }

    @Test
    void testSaveUsuarioConRolEmpleadoCreadoPorEmpleadoLanzaExcepcion(){

        //empleado a crear
        Usuario usuario = new Usuario();
        usuario.setCorreo("test@correo.com");
        usuario.setDocumentoIdentidad("123456789");
        usuario.setClave("clave123");
        usuario.setNombres("Nombre empleado");
        usuario.setFechaNacimiento(LocalDate.of(2000,1,1));

        Long idUsuarioSesion = 100L;
        Usuario usuarioAutenticado = new Usuario();
        usuarioAutenticado.setId(idUsuarioSesion);
        usuarioAutenticado.setRol(new Rol(ROL_EMPLEADO,"EMPLEADO"));

        when(usuarioSesionServicePort.obtenerIdUsuarioAutenticado()).thenReturn(idUsuarioSesion);
        when(usuarioPersistencePort.getUsuario(idUsuarioSesion)).thenReturn(usuarioAutenticado);

        Exception exception = assertThrows(UsuarioSinPermisoException.class, () -> {
            usuarioUseCase.saveUsuario(usuario, ROL_EMPLEADO);
        });

    }

    @Test
    void testSaveUsuarioConRolEmpleadoCreadoPorPropietario(){

        //empleado a crear
        Usuario usuario = new Usuario();
        usuario.setCorreo("test@correo.com");
        usuario.setDocumentoIdentidad("123456789");
        usuario.setClave("clave123");
        usuario.setNombres("Nombre empleado");

        // propietario
        Long idUsuarioSesion = 100L;
        Usuario usuarioAutenticado = new Usuario();
        usuarioAutenticado.setId(idUsuarioSesion);
        usuarioAutenticado.setRol(new Rol(ROL_PROPIETARIO,"PROPIETARIO"));

        when(usuarioSesionServicePort.obtenerIdUsuarioAutenticado()).thenReturn(idUsuarioSesion);
        when(usuarioPersistencePort.getUsuario(idUsuarioSesion)).thenReturn(usuarioAutenticado);
        when(rolPersistencePort.getRol(ROL_EMPLEADO)).thenReturn(new Rol(ROL_EMPLEADO,"EMPLEADO"));
        when(restaurantePersistencePort.getByPropietario(idUsuarioSesion)).thenReturn(new Restaurante());
        when(usuarioPersistencePort.saveUsuario(usuario)).thenReturn(usuario);

        Usuario empeladoCreado = usuarioUseCase.saveUsuario(usuario, ROL_EMPLEADO);


        assertNotNull(empeladoCreado);
        assertEquals(empeladoCreado.getRol().getId(), ROL_EMPLEADO);

    }

    @Test
    void testSaveUsuarioConRolCliente(){

        //cliente a crear
        Usuario usuario = new Usuario();
        usuario.setCorreo("test@correo.com");
        usuario.setDocumentoIdentidad("123456789");
        usuario.setClave("clave123");
        usuario.setNombres("Nombre Propietario");
        usuario.setFechaNacimiento(LocalDate.of(2000,1,1));

        Rol rolCliente = new Rol(ROL_CLIENTE,"CLIENTE");

        Usuario usuarioCreado = new Usuario();
        usuarioCreado.setId(20L);
        usuarioCreado.setRol(rolCliente);

        when(rolPersistencePort.getRol(ROL_CLIENTE)).thenReturn(rolCliente);
        when(usuarioPersistencePort.saveUsuario(any(Usuario.class))).thenReturn(usuarioCreado);

        Usuario rtaUseCase = usuarioUseCase.saveUsuario(usuario, ROL_CLIENTE);

        // Assert
        assertNotNull(rtaUseCase);
        assertEquals(rtaUseCase.getRol().getId(), ROL_CLIENTE);

    }


//
//    @Test
//    void testCrreoExistente(){
//
//        //usuario a crear
//        Usuario usuario = new Usuario();
//        usuario.setCorreo("test@correo.com");
//        usuario.setDocumentoIdentidad("123456789");
//        usuario.setClave("clave123");
//        usuario.setNombres("Nombre Propietario");
//
////        Rol rol = new Rol(idRolEmpleado,"EMPLEADO");
//        Long idRolIncorrecto = 3L;
//        Long idRol = 2L;
//
//
//        // Simula que el correo ya existe
//        when(usuarioPersistencePort.existsByCorreo(usuario.getCorreo())).thenReturn(true);
//
//
//        // Act
//
//
//        // Act & Assert
//        CorreoYaRegistradoException exception = assertThrows(
//                CorreoYaRegistradoException.class,() -> usuarioUseCase.saveUsuario(usuario, idRol)
//        );
//
//        assertEquals("Ya existe este correo registrado", exception.getMessage());
//
//        // Verifica que no continúe con la creación
//        verify(usuarioPersistencePort, never()).saveUsuario(any());
//
//
//
//        //el usuario quedara con el rol propietario
//
//
//    }


//
//
//    @Test
//    void testSaveUsuarioConRolEmpleado() {
//        // Arrange
//        Long idRolEmpleado = 3L;
//        Long idPropietario = 10L;
//        Long idRestaurante = 99L;
//
//        Usuario usuario = new Usuario();
//        usuario.setCorreo("test@correo.com");
//        usuario.setDocumentoIdentidad("123456789");
//        usuario.setClave("clave123");
//
//        Rol rol = new Rol(idRolEmpleado,"EMPLEADO");
//
//
//        Restaurante restauranteMock = new Restaurante();
//        restauranteMock.setId(idRestaurante);
//
//        Usuario usuarioCreado = new Usuario();
//        usuarioCreado.setId(20L);
//
//        // Mocking
//        when(usuarioSesionServicePort.obtenerIdUsuarioAutenticado()).thenReturn(idPropietario);
//        when(usuarioPersistencePort.existsByCorreo(anyString())).thenReturn(false);
//        when(usuarioPersistencePort.existsByDocumento(anyString())).thenReturn(false);
//        when(rolPersistencePort.getRol(idRolEmpleado)).thenReturn(rol);
//        when(encriptarContrasenaServicePort.encriptar(anyString())).thenReturn("encriptada");
//        when(usuarioPersistencePort.saveUsuario(any(Usuario.class))).thenReturn(usuarioCreado);
//        when(restaurantePersistencePort.getByPropietario(idPropietario)).thenReturn(restauranteMock);
//
//        // Act
//        Usuario resultado = usuarioUseCase.saveUsuario(usuario, idRolEmpleado);
//
//        // Assert
//        assertNotNull(resultado);
//        assertEquals(usuarioCreado.getId(), resultado.getId());
//
//        verify(usuarioSesionServicePort).obtenerIdUsuarioAutenticado();
//        verify(usuarioRestaurantePersistencePort).saveUsuarioRestaurante(any());
//    }
//
//    @Test
//    void testSaveUsuarioConRolCliente_NoLlamaSesionService() {
//        // Arrange
//        Long idRolCliente = 4L;
//
//        Usuario usuario = new Usuario();
//        usuario.setCorreo("cliente@correo.com");
//        usuario.setDocumentoIdentidad("123456789");
//        usuario.setClave("clave");
//
//        Rol rol = new Rol(idRolCliente,"CLIENTE");
//
//        Usuario usuarioCreado = new Usuario();
//        usuarioCreado.setId(21L);
//
//        when(usuarioPersistencePort.existsByCorreo(anyString())).thenReturn(false);
//        when(usuarioPersistencePort.existsByDocumento(anyString())).thenReturn(false);
//        when(rolPersistencePort.getRol(idRolCliente)).thenReturn(rol);
//        when(encriptarContrasenaServicePort.encriptar(anyString())).thenReturn("encriptada");
//        when(usuarioPersistencePort.saveUsuario(any(Usuario.class))).thenReturn(usuarioCreado);
//
//        // Act
//        Usuario resultado = usuarioUseCase.saveUsuario(usuario, idRolCliente);
//
//        // Assert
//        assertNotNull(resultado);
//        assertEquals(21L, resultado.getId());
//
//        verify(usuarioSesionServicePort, never()).obtenerIdUsuarioAutenticado();
//        verify(usuarioRestaurantePersistencePort, never()).saveUsuarioRestaurante(any());
//    }
}
