package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IEncriptarContrasenaServicePort;
import com.pragma.powerup.domain.exception.LoginFailException;
import com.pragma.powerup.domain.model.Usuario;
import com.pragma.powerup.domain.spi.IUsuarioPersistencePort;
import com.pragma.powerup.infrastructure.exception.UsuarioNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseTest {

    @InjectMocks
    private LoginUseCase loginUseCase;

    @Mock
    private IUsuarioPersistencePort usuarioPersistencePort;

    @Mock
    private IEncriptarContrasenaServicePort encriptarContrasenaServicePort;

    // ✅ 1. Login exitoso
    @Test
    void login_CredencialesCorrectas_devuelveUsuarioYReiniciaIntentos() {
        String correo = "correo@test.com";
        String clave = "clave123";

        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setClave("hashedPassword");
        usuario.setIntentos(1);

        when(usuarioPersistencePort.findByCorreo(correo)).thenReturn(usuario);
        when(encriptarContrasenaServicePort.compara(clave, "hashedPassword")).thenReturn(true);

        Usuario resultado = loginUseCase.login(correo, clave);

        assertNotNull(resultado);
        assertEquals(0, resultado.getIntentos());
        verify(usuarioPersistencePort).updateUsuario(usuario);
    }

    // 2. Clave incorrecta
    @Test
    void login_ClaveIncorrecta_incrementaIntentosYLanzaExcepcion() {
        String correo = "correo@test.com";
        String claveIncorrecta = "123";

        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setClave("hashedPassword");
        usuario.setIntentos(1);

        when(usuarioPersistencePort.findByCorreo(correo)).thenReturn(usuario);
        when(encriptarContrasenaServicePort.compara(claveIncorrecta, "hashedPassword")).thenReturn(false);

        assertThrows(LoginFailException.class, () -> loginUseCase.login(correo, claveIncorrecta));
        assertEquals(2, usuario.getIntentos());
        verify(usuarioPersistencePort).updateUsuario(usuario);
    }

    // 3. Intentos agotados
    @Test
    void login_IntentosAgotados_lanzaExcepcionSinVerificarClave() {
        String correo = "correo@test.com";

        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setIntentos(3);

        when(usuarioPersistencePort.findByCorreo(correo)).thenReturn(usuario);

        assertThrows(LoginFailException.class, () -> loginUseCase.login(correo, "cualquierClave"));

        // No verifica clave ni actualiza usuario
        verify(encriptarContrasenaServicePort, never()).compara(any(), any());
        verify(usuarioPersistencePort, never()).updateUsuario(usuario);
    }

    // 4. Correo no existe
    @Test
    void login_CorreoIncorrectoLanzaExcepcion() {

        String correo = "correo@test.com";


        when(usuarioPersistencePort.findByCorreo(correo))
                .thenThrow(new UsuarioNotFoundException("Usuario no encontrado"));

        assertThrows(UsuarioNotFoundException.class, () -> loginUseCase.login(correo, "123"));

        // No verifica clave ni actualiza usuario
        verify(encriptarContrasenaServicePort, never()).compara(any(), any());
        verify(usuarioPersistencePort, never()).updateUsuario(any());
    }





}