package com.pragma.powerup.domain.model;

public class PlatoPaginado {

    private Long categoria;
    private int pagina;
    private int tamanio;

    public PlatoPaginado(Long categoria, int pagina, int tamanio) {
        this.categoria = categoria;
        this.pagina = pagina;
        this.tamanio = tamanio;
    }

    public Long getCategoria() {
        return categoria;
    }

    public void setCategoria(Long categoria) {
        this.categoria = categoria;
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
