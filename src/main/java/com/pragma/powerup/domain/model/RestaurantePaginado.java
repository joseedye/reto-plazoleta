package com.pragma.powerup.domain.model;

public class RestaurantePaginado {


    private int pagina;
    private int tamanio;

    public RestaurantePaginado(int pagina, int tamanio) {
        this.pagina = pagina;
        this.tamanio = tamanio;
    }

    public RestaurantePaginado() {

    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public int getTamanio() {
        return tamanio;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }
}
