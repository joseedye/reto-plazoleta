package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Plato;

import java.util.List;

public interface IPlatoServicePort {

    Plato savePlato (Plato plato);
    List<Plato> getAllPlatos();
    List<Plato> listarPlatos(int pagina,int tamanio,Long categoria);
    Plato getPlato(Long id);
    Plato updatePlato(Plato plato);
    Plato habilitarPlato (Long id);
    Plato deshabilitarPlato (Long id);

}
