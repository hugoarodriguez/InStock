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
    private String nombreUsuario;

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Usuario(String correoUsuario, String nombreUsuario){
        this.correoUsuario = correoUsuario;
        this.nombreUsuario = nombreUsuario;
    }

    @NonNull
    @Override
    public String toString() {
        return "Usuario{" +
                "correoUsuario='" + correoUsuario + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                '}';
    }
}
