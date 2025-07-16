package com.pragma.powerup.infrastructure.out.jpa.adapter;


import com.pragma.powerup.domain.model.PedidoDetalle;
import com.pragma.powerup.domain.model.Plato;
import com.pragma.powerup.domain.spi.IPlatoPersistencePort;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.exception.PlatoNotFoundException;
import com.pragma.powerup.infrastructure.out.jpa.entity.PlatoEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestauranteEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IPlatoEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IPlatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PlatoJpaAdapter  implements IPlatoPersistencePort {

    private final IPlatoRepository platoRepository;
    private final IPlatoEntityMapper platoEntityMapper;

    @Override
    public Plato savePlato(Plato plato) {
       return platoEntityMapper.toPlato(platoRepository.save(platoEntityMapper.toEntity(plato)));
    }

    @Override
    public List<Plato> getAllPlatos() {
        List <PlatoEntity> platoEntityList = platoRepository.findAll();
        if(platoEntityList.isEmpty()){
            throw new NoDataFoundException();
        }
        return platoEntityMapper.toPlatoList(platoEntityList);
    }

    @Override
    public List<Plato> listarPlatos(int pagina, int tamanio, Long categoria) {

        Pageable pageable =  PageRequest.of(pagina, tamanio, Sort.by("nombre").ascending());

        Page<PlatoEntity> platoEntities = platoRepository.findAllByCategoria_Id(categoria , pageable);

        if(platoEntities.isEmpty()){
            throw new NoDataFoundException();
        }

        return platoEntities.getContent().stream()
                .map(platoEntityMapper::toPlato)
                .collect(Collectors.toList());
    }

    @Override
    public Plato getPlato(Long id) {
        return platoEntityMapper.toPlato(platoRepository.findByid(id)
                .orElseThrow(PlatoNotFoundException::new));
    }

    @Override
    public Plato updatePlato(Plato plato) {
        return platoEntityMapper.toPlato(platoRepository.save(platoEntityMapper.toEntity(plato)));

    }

    @Override
    public boolean todosPertenecenARestaurante(Long restautanteId, List<PedidoDetalle> platos) {

        for (PedidoDetalle plato : platos) {

           PlatoEntity platoEntity =  platoRepository.findById(plato.getPlatoId()).orElseThrow(PlatoNotFoundException::new);
           Long restauranteDelPlato = platoEntity.getRestaurante().getId();

            if (!restautanteId.equals(restauranteDelPlato)) {
                return false;
            }
        }
        return true;
    }


}
