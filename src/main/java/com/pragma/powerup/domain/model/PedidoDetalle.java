package com.pragma.powerup.domain.model;

public class PedidoDetalle {
    private Long id;
    private Long platoId;
    private Long pedidoId;
    private int cantidad;

    public PedidoDetalle(){

    }

    public PedidoDetalle(Long id, Long platoId, Long pedidoId, int cantidad) {
        this.id = id;
        this.platoId = platoId;
        this.pedidoId = pedidoId;
        this.cantidad = cantidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlatoId() {
        return platoId;
    }

    public void setPlatoId(Long platoId) {
        this.platoId = platoId;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
