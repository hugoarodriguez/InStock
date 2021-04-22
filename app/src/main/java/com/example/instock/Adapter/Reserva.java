package com.example.instock.Adapter;

public class Reserva {


    private String NomProducto;
    private String Precio;
    private String Cliente;


    public Reserva(String nomProducto, String precio, String cliente) {

        NomProducto = nomProducto;
        Precio = precio;
        Cliente = cliente;
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

    public void setCantidad(String cliente) {  Cliente = cliente; }

    @Override
    public String toString() {
        return "Reserva{" +
                "NomProducto='" + NomProducto + '\'' +
                ", Cliente='" + Cliente + '\'' +
                ", Precio='" + Precio + '\'' +
                '}';
    }
}
