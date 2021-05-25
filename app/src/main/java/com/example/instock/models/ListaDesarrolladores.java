package com.example.instock.models;

public class ListaDesarrolladores {
    String color;
    String nombre;
    String carnet;


    public String getColor()
    {
    return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getCarnet()
    {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public ListaDesarrolladores(String color, String nombre, String carnet)
    {
        this.color = color;
        this.nombre = nombre;
        this.carnet = carnet;
    }

}
