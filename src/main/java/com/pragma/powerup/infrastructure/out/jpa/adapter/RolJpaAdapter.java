package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Rol;
import com.pragma.powerup.domain.spi.IRolPersistencePort;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.exception.RolNotFoundException;
import com.pragma.powerup.infrastructure.out.jpa.entity.RolEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRolEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRolRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RolJpaAdapter implements IRolPersistencePort {

    private final IRolRepository rolRepository;
    private final IRolEntityMapper rolEntityMapper;

    @Override
    public void saveRol(Rol rol) {
        rolEntityMapper.toRol(rolRepository.save(rolEntityMapper.toEntity(rol)));
    }

    @Override
    public List<Rol> getAllRol() {
        List<RolEntity> rolEntityList = rolRepository.findAll();
        if(rolEntityList.isEmpty()){
            throw new NoDataFoundException();
        }
        return rolEntityMapper.toRolList(rolEntityList);
    }

    @Override
    public Rol getRol(Long id) {
        return rolEntityMapper.toRol(
                rolRepository.findById(id)
                        .orElseThrow(()->new RolNotFoundException("Rol con id "+id+" no existe")));
    }
}
