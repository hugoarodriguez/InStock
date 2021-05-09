package com.example.instock;

public class ListaVentas {
    private String codigo;
    private String categoria;
    private String nombrePro;
    private String cantidadPro;
    private String precioPro;
    private String cliente;

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

    public String getPrecioPro() {
        return precioPro;
    }

    public void setPrecioPro(String precioPro) {
        this.precioPro = precioPro;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public ListaVentas(String codigo, String categoria, String nombrePro, String cantidadPro, String precioPro, String cliente) {

        this.codigo = codigo;
        this.categoria = categoria;
        this.nombrePro = nombrePro;
        this.cantidadPro = cantidadPro;
        this.precioPro = precioPro;
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "ListaVentas{" +
                "codigo='" + codigo + '\'' +
                ", categoria='" + categoria + '\'' +
                ", nombrePro='" + nombrePro + '\'' +
                ", cantidadPro='" + cantidadPro + '\'' +
                ", precioPro='" + precioPro + '\'' +
                ", cliente='" + cliente + '\'' +
                '}';
    }
}

