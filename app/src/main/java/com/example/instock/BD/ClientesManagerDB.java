package com.example.instock.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ClientesManagerDB {

    private Context context;

    public ClientesManagerDB(Context context){
        this.context = context;
    }

    public long agregarCliente(String nombre, String apellido, String sexo, String telefono, String correo){
        long resultado = 0l;

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("apellido", apellido);
        values.put("sexo", sexo);
        values.put("telefono", telefono);
        values.put("correo", correo);

        //En la siguiente consulta pasamos el valor "null" para el "Id" ya que este es autoincrementable
        resultado = objDB.insert("Clientes", "idCliente", values);

        //objDB.execSQL(query);
        objDB.close();

        return  resultado;
    }

    public long modificarCliente(int idCliente, String nombre, String apellido, String sexo, String telefono, String correo){
        long resultado = 0l;

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("apellido", apellido);
        values.put("sexo", sexo);
        values.put("telefono", telefono);
        values.put("correo", correo);

        //Actualizamos el cliente
        resultado = objDB.update("Clientes", values, "idCliente = ?", new String[]{String.valueOf(idCliente)});

        //objDB.execSQL(query);
        objDB.close();

        return  resultado;
    }
}
