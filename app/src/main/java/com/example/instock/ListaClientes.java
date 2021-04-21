package com.example.instock;

public class ListaClientes {

    private String nombre;
    private String telefono;
    private String correo;
    private String sexo;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public ListaClientes(String nombre, String telefono, String correo, String sexo) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "ListaClientes{" +
                "nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", sexo='" + sexo + '\'' +
                '}';
    }
}
