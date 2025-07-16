package com.pragma.powerup.domain.model;

public class UsuarioRestaurante {

    private Long id;
    private Long usuarioId;
    private Long restauranteId;


    public UsuarioRestaurante() {

    }

    public UsuarioRestaurante(Long id, Long usuarioId, Long restauranteId) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.restauranteId = restauranteId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(Long restauranteId) {
        this.restauranteId = restauranteId;
    }
}
