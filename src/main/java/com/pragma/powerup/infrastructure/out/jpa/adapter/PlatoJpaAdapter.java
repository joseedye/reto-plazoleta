package com.pragma.powerup.infrastructure.out.jpa.adapter;


import com.pragma.powerup.domain.model.GenericoPaginadoOut;
import com.pragma.powerup.domain.model.PedidoDetalle;
import com.pragma.powerup.domain.model.Plato;
import com.pragma.powerup.domain.model.PlatoPaginado;
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

import static com.pragma.powerup.infrastructure.util.ExcepcionMessageConstants.ORDENA_PLATOS;
import static com.pragma.powerup.infrastructure.util.ExcepcionMessageConstants.PAGINA_NO_EXISTE;

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
    public GenericoPaginadoOut<Plato> listarPlatos(PlatoPaginado platoPaginado) {

        Pageable pageable =  PageRequest.of(
                platoPaginado.getPagina(),
                platoPaginado.getTamanio(),
                Sort.by(ORDENA_PLATOS).ascending()
        );

        Page<PlatoEntity> platoEntitiesList = platoRepository.findAllByCategoria_Id(platoPaginado.getCategoria() , pageable);

        if (platoPaginado.getPagina() >= platoEntitiesList.getTotalPages()) {
            throw new NoDataFoundException(PAGINA_NO_EXISTE);
        }

        if(platoEntitiesList.isEmpty()){
            throw new NoDataFoundException();
        }

        List<Plato> platos =  platoEntitiesList.getContent().stream()
                .map(platoEntityMapper::toPlato)
                .collect(Collectors.toList());

        GenericoPaginadoOut <Plato> platosPaginadoOut = new GenericoPaginadoOut <Plato>();
        platosPaginadoOut.setLista(platos);
        platosPaginadoOut.setPaginaActual(platoEntitiesList.getNumber());
        platosPaginadoOut.setTotalPaginas(platoEntitiesList.getTotalPages());
        platosPaginadoOut.setTotalElementos(platoEntitiesList.getTotalElements());
        platosPaginadoOut.setTamanioPagina(platoEntitiesList.getSize());

        return platosPaginadoOut;


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
