package com.pragma.powerup.domain.model;

import java.util.Objects;

public class Rol {

    private Long id;
    private String descripcion;

    public Rol(){

    }

    public Rol(Long id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rol rol = (Rol) o;
        return Objects.equals(id, rol.id); // compara por ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
