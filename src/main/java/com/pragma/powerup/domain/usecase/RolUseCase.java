package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IRolServicePort;
import com.pragma.powerup.domain.model.Rol;
import com.pragma.powerup.domain.spi.IRolPersistencePort;
import com.pragma.powerup.domain.spi.IUsuarioPersistencePort;

import java.util.List;

public class RolUseCase implements IRolServicePort {

    private final IRolPersistencePort rolPersistencePort;


    public RolUseCase(IRolPersistencePort rolPersistencePort){
        this.rolPersistencePort = rolPersistencePort;
    }

    @Override
    public void saveRol(Rol rol) {

    }

    @Override
    public List<Rol> getAllRol() {
        return List.of();
    }

    @Override
    public Rol getRol(Long id) {
       return rolPersistencePort.getRol(id);
    }
}
