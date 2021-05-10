package com.example.instock.models;

public class ListCategorias {
    private String categorias;

    public ListCategorias(String categorias) {
        this.categorias = categorias;
    }

    public String getCategorias() {
        return categorias;
    }

    public void setCategorias(String categorias) {
        this.categorias = categorias;
    }

    @Override
    public String toString() {
        return "ListCategorias{" +
                "categorias='" + categorias + '\'' +
                '}';
    }
}
