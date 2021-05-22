package com.example.instock.models;

public class Reserva {

    private String IdReserva;
    private String FotoProd;
    private String Cliente;
    private String NomProducto;
    private String CantProd;
    private String FechaEntrega;
    private String TotalPagar;

    public Reserva(String idReserva, String nomProducto, String totalPagar, String cliente, String fotoProd,
                   String cantProd, String fechaEntrega) {

        IdReserva = idReserva;
        FotoProd = fotoProd;
        Cliente = cliente;
        NomProducto = nomProducto;
        CantProd = cantProd;
        FechaEntrega = fechaEntrega;
        TotalPagar = totalPagar;
    }

    public String getIdReserva() { return IdReserva; }

    public void setIdReserva(String idReserva) { IdReserva = idReserva; }

    public String getFotoProd() { return FotoProd; }

    public void setFotoProd(String fotoProd) { FotoProd = fotoProd; }

    public String getCliente() {  return Cliente;  }

    public void setCliente(String cliente) { Cliente = cliente; }

    public String getNomProducto() {
        return NomProducto;
    }

    public void setNomProducto(String nomProducto) {
        NomProducto = nomProducto;
    }

    public String getCantProd() { return CantProd; }

    public void setCantProd(String cantProd) { CantProd = cantProd; }

    public String getFechaEntrega() { return FechaEntrega; }

    public void setFechaEntrega(String fechaEntrega) { FechaEntrega = fechaEntrega; }

    public String getTotalPagar() {
        return TotalPagar;
    }

    public void setTotalPagar(String precio) {
        TotalPagar = precio;
    }



    @Override
    public String toString() {
        return "Reserva{" +
                "IdReserva='" + IdReserva + '\'' +
                ", FotoProd='" + FotoProd + '\'' +
                ", Cliente='" + Cliente + '\'' +
                ", NomProducto='" + NomProducto + '\'' +
                ", CantProd='" + CantProd + '\'' +
                ", FechaEntrega='" + FechaEntrega + '\'' +
                ", TotalPagar='" + TotalPagar + '\'' +
                '}';
    }
}
