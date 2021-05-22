package com.example.instock.models;

public class ListaVentas {

    private String fotoProd;
    private String codigo;
    private String categoria;
    private String nombrePro;
    private String cantidadPro;
    private String totalPago;
    private String cliente;

    public String getFotoProd() { return fotoProd; }

    public void setFotoProd(String fotoProd) { this.fotoProd = fotoProd; }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNombrePro() {
        return nombrePro;
    }

    public void setNombrePro(String nombrePro) {
        this.nombrePro = nombrePro;
    }

    public String getCantidadPro() {
        return cantidadPro;
    }

    public void setCantidadPro(String cantidadPro) {
        this.cantidadPro = cantidadPro;
    }

    public String getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(String totalPago) {
        this.totalPago = totalPago;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public ListaVentas(String codigo, String categoria, String nombrePro, String cantidadPro,
                       String totalPago, String cliente, String fotoProd) {

        this.codigo = codigo;
        this.categoria = categoria;
        this.nombrePro = nombrePro;
        this.cantidadPro = cantidadPro;
        this.totalPago = totalPago;
        this.cliente = cliente;
        this.fotoProd = fotoProd;
    }

    @Override
    public String toString() {
        return "ListaVentas{" +
                "codigo='" + codigo + '\'' +
                ", categoria='" + categoria + '\'' +
                ", nombrePro='" + nombrePro + '\'' +
                ", cantidadPro='" + cantidadPro + '\'' +
                ", totalPago='" + totalPago + '\'' +
                ", cliente='" + cliente + '\'' +
                ", fotoProd='" + fotoProd + '\'' +
                '}';
    }
}

