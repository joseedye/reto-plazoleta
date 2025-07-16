package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IEncriptarContrasenaServicePort;
import com.pragma.powerup.domain.api.IUsuarioServicePort;
import com.pragma.powerup.domain.api.IUsuarioSesionServicePort;
import com.pragma.powerup.domain.exception.*;
import com.pragma.powerup.domain.model.Rol;
import com.pragma.powerup.domain.model.Usuario;
import com.pragma.powerup.domain.model.UsuarioRestaurante;
import com.pragma.powerup.domain.spi.IRestaurantePersistencePort;
import com.pragma.powerup.domain.spi.IRolPersistencePort;
import com.pragma.powerup.domain.spi.IUsuarioPersistencePort;
import com.pragma.powerup.domain.spi.IUsuarioRestaurantePersistencePort;
import com.pragma.powerup.domain.util.RolConstants;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.pragma.powerup.domain.util.ExceptionMessageConstants.*;
import static com.pragma.powerup.domain.util.RolConstants.*;
import static com.pragma.powerup.domain.util.ValidacionConstants.EDAD_MINIMA;

public class UsuarioUseCase implements IUsuarioServicePort {

    private final IUsuarioPersistencePort usuarioPersistencePort;
    private final IRolPersistencePort rolPersistencePort;
    private final IEncriptarContrasenaServicePort encriptarContrasenaServicePort;
    private final IUsuarioRestaurantePersistencePort usuarioRestaurantePersistencePort;
    private final IRestaurantePersistencePort restaurantePersistencePort;
    private final IUsuarioSesionServicePort usuarioSesionServicePort;

    public UsuarioUseCase(IUsuarioPersistencePort usuarioPersistencePort, IRolPersistencePort rolPersistencePort, IEncriptarContrasenaServicePort encriptarContrasenaServicePort, IUsuarioRestaurantePersistencePort usuarioRestaurantePersistencePort, IRestaurantePersistencePort restaurantePersistencePort, IUsuarioSesionServicePort usuarioSesionServicePort) {
        this.usuarioPersistencePort = usuarioPersistencePort;
        this.rolPersistencePort = rolPersistencePort;
        this.encriptarContrasenaServicePort = encriptarContrasenaServicePort;
        this.usuarioRestaurantePersistencePort = usuarioRestaurantePersistencePort;
        this.restaurantePersistencePort = restaurantePersistencePort;
        this.usuarioSesionServicePort = usuarioSesionServicePort;
    }

    @Override
    public Usuario saveUsuario(Usuario usuario,Long idRol) {
        Long idUsuarioAutentiicado = ROL_DEFAULT;
        Usuario usuarioAutenticado = null;

        if(!Objects.equals(idRol, ROL_CLIENTE)){
            idUsuarioAutentiicado = usuarioSesionServicePort.obtenerIdUsuarioAutenticado();
            usuarioAutenticado = usuarioPersistencePort.getUsuario(idUsuarioAutentiicado);
        }




        if(Objects.equals(idRol, ROL_PROPIETARIO)) {

            if (usuario.getFechaNacimiento().isAfter(LocalDate.now().minusYears(EDAD_MINIMA))) {
                throw new UsuarioSinMayoriaEdadException(USUARIO_NO_MAYOR_EDAD);
            }


            if(!Objects.equals(usuarioAutenticado.getRol().getId(), ROL_ADMIN)){
                throw new UsuarioSinPermisoException(USUARIO_SIN_PERMISOS);
            }

        }



        if(Objects.equals(idRol, ROL_EMPLEADO)) {
            if(!Objects.equals(usuarioAutenticado.getRol().getId(), ROL_PROPIETARIO)){
                throw new UsuarioSinPermisoException(USUARIO_SIN_PERMISOS);
            }
        }



       boolean existe =  this.usuarioPersistencePort.existsByCorreo(usuario.getCorreo());

        if (existe){
            throw new CorreoYaRegistradoException(CORREO_YA_REGISTRADO);
        }

        Rol rol = rolPersistencePort.getRol(idRol);
        if(rol == null){
            throw new NoDataFoundException(ROL_NO_EXISTE);
        }

        if(usuarioPersistencePort.existsByDocumento(usuario.getDocumentoIdentidad())){
            throw new UsuarioAlreadyExistsException(DOCUMENTO_YA_REGISTRADO);
        }


        usuario.setRol(rol);

        String contrasenaEncriptada = encriptarContrasenaServicePort.encriptar(usuario.getClave());
        usuario.setClave(contrasenaEncriptada);

        Usuario usuarioCreado =  this.usuarioPersistencePort.saveUsuario(usuario);

        if (idRol.equals(ROL_EMPLEADO)) {

            Long restauranteId = restaurantePersistencePort.getByPropietario(idUsuarioAutentiicado).getId();
            UsuarioRestaurante usuarioRestaurante = new UsuarioRestaurante();
            usuarioRestaurante.setUsuarioId(usuarioCreado.getId());
            usuarioRestaurante.setRestauranteId(restauranteId);
            this.usuarioRestaurantePersistencePort.saveUsuarioRestaurante(usuarioRestaurante);
        }

        return usuarioCreado;

    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return this.usuarioPersistencePort.getAllUsuarios();
    }

    @Override
    public Usuario getUsuario(String documentoIdentidad) {
        return this.usuarioPersistencePort.getUsuario(documentoIdentidad);
    }

    @Override
    public Usuario getUsuario(Long id) {
        return this.usuarioPersistencePort.getUsuario(id);
    }

    @Override
    public void updateUsuario(Usuario usuario) {
        this.usuarioPersistencePort.updateUsuario(usuario);
    }

    @Override
    public void deleteUsuario(String id) {
        this.usuarioPersistencePort.deleteUsuario(id);
    }
}
