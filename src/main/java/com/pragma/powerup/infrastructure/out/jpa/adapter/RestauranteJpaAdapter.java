package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Restaurante;
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

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RestauranteJpaAdapter implements IRestaurantePersistencePort {


    private final IRestauranteRepository restauranteRepository;
    private final IRestauranteEntityMapper restauranteEntityMapper;


    @Override
    public Restaurante saveRestaurante(Restaurante restaurante) {
        return restauranteEntityMapper.toRestaurante(restauranteRepository.save(restauranteEntityMapper.toEntity(restaurante)));
    }

    @Override
    public List<Restaurante> listarRestaurantes(int pagina,int tamanio) {

        Pageable pageable = PageRequest.of(pagina,tamanio);

        Page<RestauranteEntity> restauranteEntityList = restauranteRepository.findAllByOrderByNombreAsc(pageable);

        if(restauranteEntityList.isEmpty()){
            throw new NoDataFoundException();
        }

        return restauranteEntityList.getContent().stream()
                .map(restauranteEntityMapper::toRestaurante)
                .collect(Collectors.toList());

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
