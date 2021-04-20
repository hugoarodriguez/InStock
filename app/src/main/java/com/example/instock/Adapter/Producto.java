package com.example.instock.Adapter;

public class Producto {


    private String NomProducto;
    private String Categoria;
    private String Cantidad;
    private String Precio;




    public Producto(String nomProducto, String categoria, String Cantidads, String Precios) {

        NomProducto = nomProducto;
        Categoria = categoria;
        Cantidad = Cantidads;
        Precio = Precios;

    }


    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
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

    public String getCantidad() {  return Cantidad;  }

    public void setCantidad(String cantidad) {  Cantidad = cantidad; }

    @Override
    public String toString() {
        return "Producto{" +
                "NomProducto='" + NomProducto + '\'' +
                ", Categoria='" + Categoria + '\'' +
                ", Cantidad='" + Cantidad + '\'' +
                ", Precio='" + Precio + '\'' +
                '}';
    }
}
