package com.pragma.powerup.domain.model;

import java.util.List;

public class GenericoPaginadoOut<T> {

    private List<T> lista;
    private int paginaActual;
    private int totalPaginas;
    private long totalElementos;
    private int tamanioPagina;

    public GenericoPaginadoOut(List<T> lista, int paginaActual, int totalPaginas, long totalElementos, int tamanioPagina) {
        this.lista = lista;
        this.paginaActual = paginaActual;
        this.totalPaginas = totalPaginas;
        this.totalElementos = totalElementos;
        this.tamanioPagina = tamanioPagina;
    }

    public GenericoPaginadoOut() {
    }

    public List<T> getLista() {
        return lista;
    }

    public void setLista(List<T> lista) {
        this.lista = lista;
    }

    public int getTamanioPagina() {
        return tamanioPagina;
    }

    public void setTamanioPagina(int tamanioPagina) {
        this.tamanioPagina = tamanioPagina;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public void setPaginaActual(int paginaActual) {
        this.paginaActual = paginaActual;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public long getTotalElementos() {
        return totalElementos;
    }

    public void setTotalElementos(long totalElementos) {
        this.totalElementos = totalElementos;
    }
}
