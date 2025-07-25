package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.GenericoPaginadoOut;
import com.pragma.powerup.domain.model.Plato;
import com.pragma.powerup.domain.model.PlatoPaginado;

import java.util.List;

public interface IPlatoServicePort {

    Plato savePlato (Plato plato);
    List<Plato> getAllPlatos();
    GenericoPaginadoOut<Plato> listarPlatos(PlatoPaginado platoPaginado);
    Plato getPlato(Long id);
    Plato updatePlato(Plato plato);
    Plato habilitarPlato (Long id);
    Plato deshabilitarPlato (Long id);
    Plato cambiarEstado(Long id);

}
