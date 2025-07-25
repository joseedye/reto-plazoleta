package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IRestauranteServicePort;
import com.pragma.powerup.domain.api.IUsuarioSesionServicePort;
import com.pragma.powerup.domain.exception.UsuarioNotFoundException;
import com.pragma.powerup.domain.model.GenericoPaginadoOut;
import com.pragma.powerup.domain.model.Restaurante;
import com.pragma.powerup.domain.model.RestaurantePaginado;
import com.pragma.powerup.domain.model.Usuario;
import com.pragma.powerup.domain.spi.IRestaurantePersistencePort;
import com.pragma.powerup.domain.exception.RolNoPermitidoException;
import com.pragma.powerup.domain.spi.IUsuarioPersistencePort;
import com.pragma.powerup.domain.util.RolEnum;

import java.util.List;

import static com.pragma.powerup.domain.util.ExceptionMessageConstants.*;

public class RestauranteUseCase implements IRestauranteServicePort {

    private final IRestaurantePersistencePort restaurantePersistencePort;
    private final IUsuarioPersistencePort usuarioPersistencePort;
    private final IUsuarioSesionServicePort usuarioSesionServicePort;


    public RestauranteUseCase(IRestaurantePersistencePort restaurantePersistencePort, IUsuarioPersistencePort usuarioPersistencePort, IUsuarioSesionServicePort usuarioSesionServicePort) {
        this.restaurantePersistencePort = restaurantePersistencePort;
        this.usuarioPersistencePort = usuarioPersistencePort;
        this.usuarioSesionServicePort = usuarioSesionServicePort;
    }

    @Override
    public Restaurante saveRestaurante(Restaurante restaurante) {


        Long idUsuarioAutentiicado  = usuarioSesionServicePort.obtenerIdUsuarioAutenticado();
        Usuario usuarioAutenticado  = usuarioPersistencePort.getUsuario(idUsuarioAutentiicado);
        if(usuarioAutenticado.getRol().getId() != RolEnum.ADMIN.getId()){
            throw new RolNoPermitidoException(ROL_NO_ADMIN);
        }

        Usuario usuario = usuarioPersistencePort.getUsuario(restaurante.getPropietario().getId());
        if(usuario==null){
            throw new UsuarioNotFoundException(USUARIO_NO_ENCONTRADO);
        }
        restaurante.setPropietario(usuario);


        if(usuario.getRol().getId() != RolEnum.PROPIETARIO.getId()){
            throw new RolNoPermitidoException(ROL_NO_PROPIETARIO);
        }

        restaurante.setPropietario(usuario);
        return this.restaurantePersistencePort.saveRestaurante(restaurante);
    }

    @Override
    public GenericoPaginadoOut<Restaurante>  listarRestaurantes(RestaurantePaginado restaurantePaginado) {
        return  this.restaurantePersistencePort.listarRestaurantes(restaurantePaginado);

    }

    @Override
    public Restaurante getRestaurante(Long id) {
       return this.restaurantePersistencePort.getRestaurante(id);
    }

    @Override
    public void updateRestaurante(Restaurante restaurante) {

    }

    @Override
    public void deleteRestaurante(Long id) {

    }
}
