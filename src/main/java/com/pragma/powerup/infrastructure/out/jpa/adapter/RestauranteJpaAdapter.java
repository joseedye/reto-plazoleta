package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.GenericoPaginadoOut;
import com.pragma.powerup.domain.model.Restaurante;
import com.pragma.powerup.domain.model.RestaurantePaginado;
import com.pragma.powerup.domain.spi.IRestaurantePersistencePort;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.exception.RestauranteNotFoundException;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestauranteEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestauranteEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

import static com.pragma.powerup.infrastructure.util.ExcepcionMessageConstants.ORDENA_RESTAURANTES;
import static com.pragma.powerup.infrastructure.util.ExcepcionMessageConstants.PAGINA_NO_EXISTE;

@RequiredArgsConstructor
public class RestauranteJpaAdapter implements IRestaurantePersistencePort {


    private final IRestauranteRepository restauranteRepository;
    private final IRestauranteEntityMapper restauranteEntityMapper;


    @Override
    public Restaurante saveRestaurante(Restaurante restaurante) {
        return restauranteEntityMapper.toRestaurante(restauranteRepository.save(restauranteEntityMapper.toEntity(restaurante)));
    }

    @Override
    public GenericoPaginadoOut<Restaurante> listarRestaurantes(RestaurantePaginado restaurantePaginado) {

        Pageable pageable = PageRequest.of(
                restaurantePaginado.getPagina(),
                restaurantePaginado.getTamanio(),
                Sort.by(ORDENA_RESTAURANTES).ascending()
        );

        Page<RestauranteEntity> restauranteEntityList = restauranteRepository.findAll(pageable);

        if (restaurantePaginado.getPagina() >= restauranteEntityList.getTotalPages()) {
            throw new NoDataFoundException(PAGINA_NO_EXISTE);
        }


        if(restauranteEntityList.isEmpty()){
            throw new NoDataFoundException();
        }

        List<Restaurante> restaurantes =  restauranteEntityList.getContent().stream()
                .map(restauranteEntityMapper::toRestaurante)
                .collect(Collectors.toList());


        GenericoPaginadoOut <Restaurante> restaurantePaginadoOut = new GenericoPaginadoOut <Restaurante>();
        restaurantePaginadoOut.setLista(restaurantes);
        restaurantePaginadoOut.setPaginaActual(restauranteEntityList.getNumber());
        restaurantePaginadoOut.setTotalPaginas(restauranteEntityList.getTotalPages());
        restaurantePaginadoOut.setTotalElementos(restauranteEntityList.getTotalElements());
        restaurantePaginadoOut.setTamanioPagina(restauranteEntityList.getSize());

        return restaurantePaginadoOut;

    }

    @Override
    public Restaurante getRestaurante(Long id) {
       return restauranteEntityMapper.toRestaurante(restauranteRepository.findByid(id)
               .orElseThrow(RestauranteNotFoundException::new));
    }

    @Override
    public void updateRestaurante(Restaurante restaurante) {

    }

    @Override
    public void deleteRestaurante(Long id) {

    }


    public Restaurante getByPropietario(Long id){
        return restauranteEntityMapper.toRestaurante(restauranteRepository.findBypropietario_id(id)
                .orElseThrow(RestauranteNotFoundException::new));
    }
}
