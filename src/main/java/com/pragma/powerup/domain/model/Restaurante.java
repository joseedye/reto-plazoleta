package com.pragma.powerup.domain.model;

import com.pragma.powerup.infrastructure.out.jpa.entity.UsuarioEntity;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

public class Restaurante {



    private Long Id;
    private String nombre;
    private String NIT;
    private String direccion;
    private String telefono;
    private String urlLogo;
    private Usuario propietario;

    public Restaurante(){

    }
    public Restaurante(Long id, String nombre, String NIT, String direccion, String telefono, String urlLogo, Usuario propietario) {
        Id = id;
        this.nombre = nombre;
        this.NIT = NIT;
        this.direccion = direccion;
        this.telefono = telefono;
        this.urlLogo = urlLogo;
        this.propietario = propietario;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNIT() {
        return NIT;
    }

    public void setNIT(String NIT) {
        this.NIT = NIT;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public Usuario getPropietario() {
        return propietario;
    }

    public void setPropietario(Usuario propietario) {
        this.propietario = propietario;
    }
}
