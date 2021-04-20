package com.example.instock.Adapter;

public class Producto {


    private String NomProducto;
    private String Categoria;


    public Producto( String nomProducto, String categoria) {

        NomProducto = nomProducto;
        Categoria = categoria;

    }



    public String getNomProducto() {
        return NomProducto;
    }

    public void setNomProducto(String nomProducto) {
        NomProducto = nomProducto;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }



    @Override
    public String toString() {
        return "Producto{" +

                ", NomProducto='" + NomProducto + '\'' +
                ", Categoria='" + Categoria + '\'' +

                '}';
    }
}
