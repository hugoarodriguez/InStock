package com.example.instock.models;

import androidx.annotation.NonNull;

public class Usuario {

    private static Usuario instance = null;
    private Usuario(){
        System.out.println("Singleton invoked");
    }
    private synchronized static void createInstance() {
        if (instance == null) {
            instance = new Usuario();
        }
    }
    public static Usuario getInstance() {
        if (instance == null) createInstance();
        return instance;
    }

    private String correoUsuario;
    private String nombresUsuario;
    private String apellidosUsuario;
    private String nombreEmpresa;

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getNombresUsuario() {
        return nombresUsuario;
    }

    public void setNombresUsuario(String nombresUsuario) {
        this.nombresUsuario = nombresUsuario;
    }

    public String getApellidosUsuario() { return apellidosUsuario; }

    public void setApellidosUsuario(String apellidosUsuario) { this.apellidosUsuario = apellidosUsuario; }

    public String getNombreEmpresa() { return nombreEmpresa; }

    public void setNombreEmpresa(String nombreEmpresa) { this.nombreEmpresa = nombreEmpresa; }

    public Usuario(String correoUsuario, String nombresUsuario, String apellidosUsuario,
                   String nombreEmpresa){
        this.correoUsuario = correoUsuario;
        this.nombresUsuario = nombresUsuario;
        this.apellidosUsuario = apellidosUsuario;
        this.nombreEmpresa = nombreEmpresa;
    }

    @NonNull
    @Override
    public String toString() {
        return "Usuario{" +
                "correoUsuario='" + correoUsuario + '\'' +
                ", nombreUsuario='" + nombresUsuario + '\'' +
                ", apellidosUsuario='" + apellidosUsuario + '\'' +
                ", nombreEmpresa='" + nombreEmpresa + '\'' +
                '}';
    }
}
