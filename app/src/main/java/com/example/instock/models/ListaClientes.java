package com.example.instock.models;

public class ListaClientes {

    private String idCliente;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String sexo;

    public String getIdCliente() { return idCliente; }

    public void setIdCliente(String idCliente) { this.idCliente = idCliente; }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() { return apellido; }

    public void setApellido(String apellido) { this.apellido = apellido; }

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

    public ListaClientes(String idCliente, String nombre, String telefono, String correo, String sexo) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.sexo = sexo;
    }

    public ListaClientes(String idCliente, String nombre, String apellido, String telefono, String correo, String sexo) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "ListaClientes{" +
                "idCliente='" + idCliente + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", sexo='" + sexo + '\'' +
                '}';
    }
}
