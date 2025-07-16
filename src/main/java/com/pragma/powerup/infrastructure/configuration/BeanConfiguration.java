package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.*;
import com.pragma.powerup.domain.spi.*;
import com.pragma.powerup.domain.usecase.*;
import com.pragma.powerup.infrastructure.out.jpa.adapter.*;
import com.pragma.powerup.infrastructure.out.jpa.mapper.*;
import com.pragma.powerup.infrastructure.out.jpa.repository.*;
import com.pragma.powerup.infrastructure.security.EncriptarContrasenaAdapter;
import com.pragma.powerup.infrastructure.security.adapter.UsuarioSesionAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUsuarioRepository usuarioRepository;
    private final IUsuarioEntityMapper usuarioEntityMapper;

    private final IRolRepository rolRepository;
    private final IRolEntityMapper rolEntityMapper;

    private final IObjectRepository objectRepository;
    private final IObjectEntityMapper objectEntityMapper;

    private final IRestauranteRepository restauranteRespository;
    private final IRestauranteEntityMapper restauranteEntityMapper;

    private final IPlatoRepository platoRepository;
    private final IPlatoEntityMapper platoEntityMapper;


    private final ICategoriaRepository categoriaRepository;
    private final ICategoriaEntityMapper categoriaEntityMapper;

    private final IPedidoRepository pedidoRepository;
    private final IPedidoEntityMapper pedidoEntityMapper;

    private final IUsuarioRestauranteRepository usuarioRestauranteRepository;
    private final IUsuarioRestauranteEntityMapper usuarioRestauranteEntityMapper;


    @Bean
    public IUsuarioPersistencePort usuarioPersistencePort() {
        return new UsuarioJpaAdapter(usuarioRepository,usuarioEntityMapper);
    }

    @Bean
    public IUsuarioServicePort usuarioServicePort() {
        return new UsuarioUseCase(usuarioPersistencePort(),rolPersistencePort(),encriptarcontrasena(),usuarioRestaurantePersistencPort(),restaurantePP(),usuarioSesionServicePort());
    }

    @Bean
    public IEncriptarContrasenaServicePort encriptarcontrasena(){
        return new EncriptarContrasenaAdapter(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public IRolPersistencePort rolPersistencePort(){
        return new RolJpaAdapter(rolRepository,rolEntityMapper);
    }

    @Bean
    public IRolServicePort rolServicePort(){
        return new RolUseCase(rolPersistencePort());
    }

    @Bean
    public IObjectPersistencePort objectPersistencePort() {return new ObjectJpaAdapter(objectRepository, objectEntityMapper);
    }

    @Bean
    public IObjectServicePort objectServicePort() {return new ObjectUseCase(objectPersistencePort());
    }

    @Bean
    public IRestaurantePersistencePort restaurantePP() {

        return new RestauranteJpaAdapter(restauranteRespository,restauranteEntityMapper);

    }



    @Bean
    public IRestauranteServicePort restauranteServicePort (){
        return new RestauranteUseCase(restaurantePP(),usuarioPersistencePort(),usuarioSesionServicePort());
    }

    @Bean
    public IPlatoPersistencePort platoPersistencePort(){
        return new PlatoJpaAdapter(platoRepository,platoEntityMapper);
    }

    @Bean
    public IPlatoServicePort platoServicePort(){
        return new PlatoUseCase(platoPersistencePort(),usuarioPersistencePort(),usuarioSesionServicePort());
    }

    @Bean
    public ICategoriaPersistencePort categoriaPersistencePort(){
        return new CategoriaJpaAdapter(categoriaRepository,categoriaEntityMapper);
    }

    @Bean
    public ICategoriaServicePort categoriaServicePort(){
        return new CategoriaUseCase(categoriaPersistencePort());
    }

    @Bean
    public ILoginServicePort loginServicePort(){
        return new LoginUseCase(usuarioPersistencePort(),encriptarcontrasena());
    }


    @Bean
    public IUsuarioSesionServicePort usuarioSesionServicePort(){
        return new UsuarioSesionAdapter();
    }

    @Bean
    public IPedidoServicePort pedidoServicePort(){
        return new PedidoUseCase(pedidoPersistencePort(),platoPersistencePort(),usuarioSesionServicePort(),usuarioRestaurantePersistencPort());
    }


    @Bean
    public IPedidoPersistencePort pedidoPersistencePort(){
        return new PedidoJpaAdapter(pedidoRepository,pedidoEntityMapper);
    }


    @Bean
    public IUsuarioRestaurantePersistencePort usuarioRestaurantePersistencPort(){
        return new UsuarioRestauranteJpaAdapter(usuarioRestauranteRepository,usuarioRestauranteEntityMapper);
    }


}