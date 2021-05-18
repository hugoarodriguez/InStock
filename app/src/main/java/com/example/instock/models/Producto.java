package com.example.instock.models;

import android.graphics.Bitmap;

public class Producto {

    private String IdProd;
    private String NomProducto;
    private String Categoria;
    private String Cantidad;
    private String Precio;
    private String FotoProd;

    public Producto(String idProd, String nomProducto, String categoria, String cantidad, String precio
            , String fotoProd) {

        IdProd = idProd;
        NomProducto = nomProducto;
        Categoria = categoria;
        Cantidad = cantidad;
        Precio = precio;
        FotoProd = fotoProd;
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

    @Override
    public String toString() {
        return "Producto{" +
                "IdProd='" + IdProd + '\'' +
                "NomProducto='" + NomProducto + '\'' +
                ", Categoria='" + Categoria + '\'' +
                ", Cantidad='" + Cantidad + '\'' +
                ", Precio='" + Precio + '\'' +
                ", FotoProd='" + FotoProd + '\'' +
                '}';
    }
}
