package com.example.instock.models;

import android.graphics.Bitmap;

public class Producto {

    private String IdProd;
    private String NomProducto;
    private String Categoria;
    private String Cantidad;
    private String Precio;
    private String FotoProd;
    private String Detalles;

    public Producto(String idProd, String nomProducto, String categoria, String cantidad, String precio
            , String fotoProd, String detalles) {

        IdProd = idProd;
        NomProducto = nomProducto;
        Categoria = categoria;
        Cantidad = cantidad;
        Precio = precio;
        FotoProd = fotoProd;
        Detalles = detalles;
    }

    public String getIdProd() { return IdProd; }

    public void setIdProd(String idProd) { IdProd = idProd; }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) { Precio = precio; }

    public String getNomProducto() { return NomProducto; }

    public void setNomProducto(String nomProducto) { NomProducto = nomProducto; }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public String getCantidad() {  return Cantidad;  }

    public void setCantidad(String cantidad) {  Cantidad = cantidad; }

    public String getFotoProd() { return FotoProd; }

    public void setFotoProd(String fotoProd) { FotoProd = fotoProd; }

    public String getDetalles() { return Detalles; }

    public void setDetalles(String detalles) { Detalles = detalles; }

    @Override
    public String toString() {
        return "Producto{" +
                "IdProd='" + IdProd + '\'' +
                "NomProducto='" + NomProducto + '\'' +
                ", Categoria='" + Categoria + '\'' +
                ", Cantidad='" + Cantidad + '\'' +
                ", Precio='" + Precio + '\'' +
                ", FotoProd='" + FotoProd + '\'' +
                ", Detalles='" + Detalles + '\'' +
                '}';
    }
}
