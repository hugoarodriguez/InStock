package com.example.instock.models;

public class ListCategorias {
    private String categoria;

    private int idCategoria;

    public ListCategorias(int idCategoria, String categoria) {
        this.categoria = categoria;
        this.idCategoria = idCategoria;
    }

    public int getIdCategoria() { return idCategoria; }

    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "ListCategorias{" +
                "idCategoria='" + idCategoria + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
