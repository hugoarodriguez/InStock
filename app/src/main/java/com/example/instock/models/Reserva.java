package com.example.instock.models;

public class Reserva {


    private String NomProducto;
    private String Precio;
    private String Cliente;
    private String FotoProd;
    private String CantProd;


    public Reserva(String nomProducto, String precio, String cliente, String fotoProd, String cantProd) {

        NomProducto = nomProducto;
        Precio = precio;
        Cliente = cliente;
        FotoProd = fotoProd;
        CantProd = cantProd;
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

    public String getCliente() {  return Cliente;  }

    public void setCliente(String cliente) { Cliente = cliente; }

    public void setCantidad(String cliente) {  Cliente = cliente; }

    public String getFotoProd() { return FotoProd; }

    public void setFotoProd(String fotoProd) { FotoProd = fotoProd; }

    public String getCantProd() { return CantProd; }

    public void setCantProd(String cantProd) { CantProd = cantProd; }

    @Override
    public String toString() {
        return "Reserva{" +
                "NomProducto='" + NomProducto + '\'' +
                ", Cliente='" + Cliente + '\'' +
                ", Precio='" + Precio + '\'' +
                ", FotoProd='" + FotoProd + '\'' +
                '}';
    }
}
