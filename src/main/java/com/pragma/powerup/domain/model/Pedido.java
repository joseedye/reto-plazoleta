package com.pragma.powerup.domain.model;


import java.util.List;

public class Pedido {


    private Long id;
    private Long clienteId;
    private Long restauranteId;
    private String estado;
    private Usuario empleadoAsignado;
    private List<PedidoDetalle> detalles;
    private Long pinSeguridad;

    public Pedido(){

    }

    public Pedido(Long id, Long clienteId,Long restauranteId,String estado,List<PedidoDetalle> detalles) {
        this.id = id;
        this.detalles = detalles;
        this.estado = estado;
        this.restauranteId = restauranteId;
        this.clienteId = clienteId;
    }

    public Pedido(Long id, Long clienteId, Long restauranteId, String estado, Usuario empleadoAsignado, List<PedidoDetalle> detalles, Long pinSeguridad) {
        this.id = id;
        this.clienteId = clienteId;
        this.restauranteId = restauranteId;
        this.estado = estado;
        this.empleadoAsignado = empleadoAsignado;
        this.detalles = detalles;
        this.pinSeguridad = pinSeguridad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(Long restauranteId) {
        this.restauranteId = restauranteId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<PedidoDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<PedidoDetalle> detalles) {
        this.detalles = detalles;
    }

    public Usuario getEmpleadoAsignado() {
        return empleadoAsignado;
    }

    public void setEmpleadoAsignado(Usuario empleadoAsignado) {
        this.empleadoAsignado = empleadoAsignado;
    }

    public Long getPinSeguridad() {
        return pinSeguridad;
    }

    public void setPinSeguridad(Long pinSeguridad) {
        this.pinSeguridad = pinSeguridad;
    }
}