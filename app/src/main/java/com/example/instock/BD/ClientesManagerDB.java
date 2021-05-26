package com.example.instock.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.instock.firebasemanager.FirebaseManager;
import com.example.instock.models.ListaClientes;
import com.example.instock.models.Producto;

import java.util.ArrayList;

public class ClientesManagerDB {

    private Context context;
    String userID;

    public ClientesManagerDB(Context context){
        this.context = context;
        FirebaseManager firebaseManager = new FirebaseManager();
        //Obtenemos el id del Usuario que se ha registrado
        userID = firebaseManager.getCurrentUserId();
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
        values.put("estadoCliente", 1);
        values.put("userID", userID);

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

    //Método para obtener el listado de Clientes (únicamente los activos)
    public ArrayList<ListaClientes> obtenerClientes(){
        ListaClientes cliente;
        ArrayList<ListaClientes> clientes = new ArrayList<>();

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        // Creamos Cursor
        Cursor cursor = objDB.rawQuery("SELECT * FROM Clientes WHERE estadoCliente = ? AND userID = ?",
                new String[]{"1", userID});
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

    //Método para obtener el listado de Clientes según correo
    public ArrayList<ListaClientes> obtenerClientesLike(String correo){
        ListaClientes cliente;
        ArrayList<ListaClientes> clientes = new ArrayList<>();

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        // Creamos Cursor
        Cursor cursor = objDB.rawQuery("SELECT * FROM Clientes WHERE estadoCliente = ? " +
                        "AND correo LIKE ? AND userID = ?",
                new String[]{"1", correo+"%", userID});
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

    //Método para obtener el listado de correos de los Clientes
    public ArrayList<String> obtenerCorreosClientes(){
        ArrayList<String> nombresClientes = new ArrayList<>();

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        // Creamos Cursor
        Cursor cursor = objDB.rawQuery("SELECT * FROM Clientes WHERE estadoCliente = ? AND userID = ?",
                new String[]{"1", userID});
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            nombresClientes.add(cursor.getString(cursor.getColumnIndex("correo")));
            cursor.moveToNext();
        }

        return nombresClientes;
    }

    //Método para obtener los datos de un Cliente
    public ListaClientes obtenerCliente(String idCliente){
        CategoriasManagerDB categoriasManagerDB = new CategoriasManagerDB(context);
        ListaClientes cliente = null;

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        // Creamos Cursor
        Cursor cursor = objDB.rawQuery("SELECT * FROM Clientes WHERE idCliente = ? AND estadoCliente = ?",
                new String[]{idCliente, "1"});

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
        CategoriasManagerDB categoriasManagerDB = new CategoriasManagerDB(context);
        ListaClientes cliente = null;

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        // Creamos Cursor
        Cursor cursor = objDB.rawQuery("SELECT idCliente FROM Clientes WHERE correo = ? AND estadoCliente = ? " +
                        "AND userID = ?"
                ,new String[]{correo, "1", userID});

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
            ContentValues values = new ContentValues();
            values.put("estadoCliente", 2);

            //Eliminamos el cliente según el "idCliente" (unicamente cambiamos el valor de su estado)
            resultado = objDB.update("Clientes", values,"idCliente = ?", new String[]{String.valueOf(idCliente)});
        }

        objDB.close();

        return resultado;
    }
}
