package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IPlatoServicePort;
import com.pragma.powerup.domain.api.IUsuarioSesionServicePort;
import com.pragma.powerup.domain.exception.NoDataFoundException;
import com.pragma.powerup.domain.exception.RestauranteNoPermitidoException;
import com.pragma.powerup.domain.exception.UsuarioSinPermisoException;
import com.pragma.powerup.domain.model.GenericoPaginadoOut;
import com.pragma.powerup.domain.model.Plato;
import com.pragma.powerup.domain.model.PlatoPaginado;
import com.pragma.powerup.domain.model.Usuario;
import com.pragma.powerup.domain.spi.IPlatoPersistencePort;
import com.pragma.powerup.domain.spi.IUsuarioPersistencePort;
import com.pragma.powerup.domain.util.RolEnum;

import java.util.List;

import static com.pragma.powerup.domain.util.ExceptionMessageConstants.*;

public class PlatoUseCase implements IPlatoServicePort {

    private final IPlatoPersistencePort platoPersistencePort;
    private final IUsuarioPersistencePort usuarioPersistencePort;
    private final IUsuarioSesionServicePort usuarioSesionServicePort;

    public PlatoUseCase(IPlatoPersistencePort platoPersistencePort, IUsuarioPersistencePort usuarioPersistencePort, IUsuarioSesionServicePort usuarioSesionServicePort) {
        this.platoPersistencePort = platoPersistencePort;
        this.usuarioPersistencePort = usuarioPersistencePort;
        this.usuarioSesionServicePort = usuarioSesionServicePort;
    }

    @Override
    public Plato savePlato(Plato plato) {

        Long idUsuarioAutenticado = usuarioSesionServicePort.obtenerIdUsuarioAutenticado();
        Usuario propietario = usuarioPersistencePort.getUsuario(idUsuarioAutenticado);

        if(propietario.getRol().getId() != RolEnum.PROPIETARIO.getId()){
            throw new UsuarioSinPermisoException(USUARIO_SIN_PERMISOS);
        }

        if(!plato.getRestaurante().getPropietario().equals(propietario)){
            throw new RestauranteNoPermitidoException(NO_PROPIETARIO_RESTAURANTE);
        }

        plato.setEstado(true);

        return this.platoPersistencePort.savePlato(plato);

    }

    @Override
    public List<Plato> getAllPlatos() {
        return this.platoPersistencePort.getAllPlatos();
    }

    @Override
    public GenericoPaginadoOut<Plato> listarPlatos(PlatoPaginado platoPaginado) {
        return this.platoPersistencePort.listarPlatos(platoPaginado);
    }

    @Override
    public Plato getPlato(Long id) {
        return platoPersistencePort.getPlato(id);
    }

    @Override
    public Plato updatePlato(Plato platoIn) {

        Plato plato = this.platoPersistencePort.getPlato(platoIn.getId());
        Long idUsuarioAutenticado = usuarioSesionServicePort.obtenerIdUsuarioAutenticado();
        Usuario usuariosesion =  usuarioPersistencePort.getUsuario(idUsuarioAutenticado);


        if(usuariosesion.getRol().getId() != RolEnum.PROPIETARIO.getId()){
            throw new UsuarioSinPermisoException(USUARIO_SIN_PERMISOS);
        }

        if(!plato.getRestaurante().getPropietario().equals(usuariosesion)){
            throw new NoDataFoundException(NO_PROPIETARIO_RESTAURANTE);

        }


        plato.setDescripcion(platoIn.getDescripcion());
        plato.setPrecio(platoIn.getPrecio());
        return this.platoPersistencePort.updatePlato(plato);

    }

    @Override
    public Plato habilitarPlato(Long id) {

        Plato plato = this.platoPersistencePort.getPlato(id);
        if (plato.isEstado()) {
            return plato;
        }

        Long idUsuarioAutentiicado = usuarioSesionServicePort.obtenerIdUsuarioAutenticado();
        if(plato.getRestaurante().getPropietario().getId()!=idUsuarioAutentiicado){
            throw new UsuarioSinPermisoException(MODIFICA_PLATO_NO_PERMITIDA);
        }


        plato.setEstado(true);
        return this.platoPersistencePort.updatePlato(plato);
    }

    @Override
    public Plato deshabilitarPlato(Long id) {

        Plato plato = this.platoPersistencePort.getPlato(id);
        if(plato==null){
            throw new NoDataFoundException(PLATO_NO_EXISTE);
        }

        if (!plato.isEstado()) {
            return plato;
        }

        Long idUsuarioAutentiicado = usuarioSesionServicePort.obtenerIdUsuarioAutenticado();
        if(plato.getRestaurante().getPropietario().getId()!=idUsuarioAutentiicado){
            throw new UsuarioSinPermisoException(MODIFICA_PLATO_NO_PERMITIDA);
        }


        plato.setEstado(false);
        return this.platoPersistencePort.updatePlato(plato);
    }




    @Override
    public Plato cambiarEstado(Long id) {

        Plato plato = this.platoPersistencePort.getPlato(id);
        if(plato==null){
            throw new NoDataFoundException(PLATO_NO_EXISTE);
        }


        Long idUsuarioAutentiicado = usuarioSesionServicePort.obtenerIdUsuarioAutenticado();
        if(plato.getRestaurante().getPropietario().getId()!=idUsuarioAutentiicado){
            throw new UsuarioSinPermisoException(MODIFICA_PLATO_NO_PERMITIDA);
        }


        plato.setEstado(!plato.isEstado());
        return this.platoPersistencePort.updatePlato(plato);

    }
}
