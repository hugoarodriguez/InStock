package com.example.instock.models;

public class ListCategorias {

    private int idCategoria;
    private String categoria;
    private int estadoCategoria;

    public ListCategorias(int idCategoria, String categoria, int estadoCategoria) {
        this.categoria = categoria;
        this.idCategoria = idCategoria;
        this.estadoCategoria = estadoCategoria;
    }

    public int getIdCategoria() { return idCategoria; }

    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getEstadoCategoria() { return estadoCategoria; }

    public void setEstadoCategoria(int estadoCategoria) { this.estadoCategoria = estadoCategoria; }

    @Override
    public String toString() {
        return "ListCategorias{" +
                "idCategoria='" + idCategoria + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
