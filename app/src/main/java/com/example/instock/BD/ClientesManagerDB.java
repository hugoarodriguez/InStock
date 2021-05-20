package com.example.instock.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.instock.models.ListaClientes;
import com.example.instock.models.Producto;

import java.util.ArrayList;

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

    public int modificarCliente(int idCliente, String nombre, String apellido, String sexo, String telefono, String correo){
        int resultado = 0;

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

        objDB.close();

        return  resultado;
    }

    //Método para obtener el listado de productos
    public ArrayList<ListaClientes> obtenerClientes(){
        ListaClientes cliente;
        ArrayList<ListaClientes> clientes = new ArrayList<>();

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        // Creamos Cursor
        Cursor cursor = objDB.rawQuery("SELECT * FROM Clientes",null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            cliente = new ListaClientes(cursor.getString(cursor.getColumnIndex("idCliente")),
                    cursor.getString(cursor.getColumnIndex("nombre")) + " "
                            + cursor.getString(cursor.getColumnIndex("apellido")),
                    cursor.getString(cursor.getColumnIndex("telefono")),
                    cursor.getString(cursor.getColumnIndex("correo")),
                    cursor.getString(cursor.getColumnIndex("sexo")));

            clientes.add(cliente);
            cursor.moveToNext();
        }

        return clientes;
    }

    //Método para obtener el listado de productos
    public ArrayList<String> obtenerCorreosClientes(){
        ArrayList<String> nombresClientes = new ArrayList<>();

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        // Creamos Cursor
        Cursor cursor = objDB.rawQuery("SELECT * FROM Clientes",null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            nombresClientes.add(cursor.getString(cursor.getColumnIndex("correo")));
            cursor.moveToNext();
        }

        return nombresClientes;
    }

    //Método para obtener los datos de un Cliente
    public ListaClientes obtenerCliente(String idCliente){
        CategoriasManagerDB categoriasManagerDB = new CategoriasManagerDB();
        ListaClientes cliente = null;

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        // Creamos Cursor
        Cursor cursor = objDB.rawQuery("SELECT * FROM Clientes WHERE idCliente = ?",new String[]{idCliente});

        if (cursor.moveToFirst()) {
            cliente = new ListaClientes(cursor.getString(cursor.getColumnIndex("idCliente")),
                    cursor.getString(cursor.getColumnIndex("nombre")),
                    cursor.getString(cursor.getColumnIndex("apellido")),
                    cursor.getString(cursor.getColumnIndex("telefono")),
                    cursor.getString(cursor.getColumnIndex("correo")),
                    cursor.getString(cursor.getColumnIndex("sexo")));
        }

        return cliente;
    }

    //Método para obtener el id de un Cliente según su Correo
    public int getIdClienteByCorreo(String correo){
        int idCliente = 0;
        CategoriasManagerDB categoriasManagerDB = new CategoriasManagerDB();
        ListaClientes cliente = null;

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        // Creamos Cursor
        Cursor cursor = objDB.rawQuery("SELECT idCliente FROM Clientes WHERE correo = ?",new String[]{correo});

        if (cursor.moveToFirst()) {
            idCliente = cursor.getInt(cursor.getColumnIndex("idCliente"));
        }

        return idCliente;
    }

    //Método para eliminar un Cliente
    public int eliminarCliente(int idCliente){
        int resultado = 0;

        // Creamos objeto de la clase Base
        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getWritableDatabase();

        //Verificamos si existe el cliente según su ID
        Cursor cursor = objDB.rawQuery("SELECT * FROM Clientes WHERE idCliente = ?", new String[]{String.valueOf(idCliente)});
        if (cursor.moveToNext()) {
            //Eliminamos el cliente según el "idCliente"
            resultado = objDB.delete("Clientes", "idCliente = ?", new String[]{String.valueOf(idCliente)});
        }

        objDB.close();

        return resultado;
    }
}
