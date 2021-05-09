package com.example.instock.models;

public class ModalDialogValues {

    private static ModalDialogValues instance = null;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    private String titulo, mensaje;

    private ModalDialogValues(){
        System.out.println("Singleton invoked");
    }
    private synchronized static void createInstance() {
        if (instance == null) {
            instance = new ModalDialogValues();
        }
    }
    public static ModalDialogValues getInstance() {
        if (instance == null) createInstance();
        return instance;
    }

    //Mostrar mensaje de alerta
    public void modalDialogValues(String titulo, String mensaje){
        this.titulo = titulo;
        this.mensaje = mensaje;
    }
}
