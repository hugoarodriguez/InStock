package com.example.instock.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.instock.R;
import com.example.instock.firebasemanager.FirebaseManager;
import com.example.instock.models.ListCategorias;

import java.util.ArrayList;

public class CategoriasManagerDB {

    private Context context;
    String userID;

    public CategoriasManagerDB(Context context){
        this.context = context;

        FirebaseManager firebaseManager = new FirebaseManager();
        //Obtenemos el id del Usuario que se ha registrado
        userID = firebaseManager.getCurrentUserId();
    }

    public long agregarCategoria(String nomCategoria){
        long resultado = 0;
        // Creamos objeto de la clase Base
        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getWritableDatabase();

        //Verificamos que no haya una categoría existente con el mismo nombre
        Cursor cursor = objDB.rawQuery("SELECT * FROM Categorias WHERE categoria = ? AND estadoCategoria = ?" +
                        " AND userID = ?",
                new String[]{nomCategoria.trim().toUpperCase(), "1", userID});
        if (!cursor.moveToNext()) {
            ContentValues values = new ContentValues();
            values.put("categoria", nomCategoria.trim().toUpperCase());//El valor "2" en el campo "estadoCategoria" indica "Eliminada"
            values.put("estadoCategoria", 1);//El valor "2" en el campo "estadoCategoria" indica "Eliminada"
            values.put("userID", userID);//El valor "2" en el campo "estadoCategoria" indica "Eliminada"

            //Establecemos como "eliminada" la categoría según su ID
            resultado = objDB.insert("Categorias", "idCategoria", values);
        } else {
            resultado = -2;//Indica que ya hay una Categoria con ese nombre
        }

        objDB.close();

        return resultado;
    }

    public int modificarCategoria(int idCategoria, String nomCategoria){
        int resultado = 0;

        // Creamos objeto de la clase Base
        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getWritableDatabase();

        //Verificamos que no haya una categoría existente con el mismo nombre
        Cursor cursor = objDB.rawQuery("SELECT * FROM Categorias WHERE idCategoria = ?", new String[]{String.valueOf(idCategoria)});
        if (cursor.moveToNext()) {
            ContentValues values = new ContentValues();
            values.put("categoria", nomCategoria.trim().toUpperCase());//El valor "2" en el campo "estadoCategoria" indica "Eliminada"

            //Establecemos como "eliminada" la categoría según su ID
            resultado = objDB.update("Categorias", values, "idCategoria = ?",
                    new String[]{String.valueOf(idCategoria)} );
        } else {
            resultado = -2;//Indica que ya NO existe esa Categoria
        }

        objDB.close();

        return resultado;
    }

    //Método para obtener listado de Categorías
    public ArrayList<ListCategorias> getCategorias(){
        ArrayList<ListCategorias> listCategorias = new ArrayList<>();

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getWritableDatabase();

        // Creamos Cursor
        Cursor cursor = objDB.rawQuery("SELECT * FROM Categorias WHERE estadoCategoria = ?" +
                        " AND userID = ?",
                new String[]{"1", userID});
        cursor.moveToFirst();
        int index = 0;
        while (cursor.isAfterLast() == false) {
            listCategorias.add(new ListCategorias(
                    cursor.getInt(cursor.getColumnIndex("idCategoria")),
                    cursor.getString(cursor.getColumnIndex("categoria")),
                    cursor.getInt(cursor.getColumnIndex("estadoCategoria"))));
            cursor.moveToNext();
        }

        return  listCategorias;
    }

    //Método para obtener listado de Categorías (para Spinner)
    public ArrayList<String> getCategoriasStringArray(){
        ListCategorias listCategorias;
        ArrayList<String> listCategoriasString = new ArrayList<>();

        String primerValor = context.getResources().getString(R.string.spr_categoria_first_value);
        listCategoriasString.add(primerValor);

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getWritableDatabase();

        // Creamos Cursor
        Cursor cursor = objDB.rawQuery("SELECT * FROM Categorias WHERE estadoCategoria = ?" +
                        " AND userID = ?",
                new String[]{"1", userID});
        cursor.moveToFirst();
        int index = 0;
        while (cursor.isAfterLast() == false) {
            listCategorias = new ListCategorias(
                    cursor.getInt(cursor.getColumnIndex("idCategoria")),
                    cursor.getString(cursor.getColumnIndex("categoria")),
                    cursor.getInt(cursor.getColumnIndex("estadoCategoria")));
            listCategoriasString.add(listCategorias.getCategoria());
            cursor.moveToNext();
        }

        return  listCategoriasString;
    }

    //Método para obtener el idCategoria según el nombre de la Categoria
    public int getIDCategoriaByName(String categoria){
        int idCategoria = 0;

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        Cursor cursor = objDB.rawQuery("SELECT idCategoria FROM Categorias WHERE categoria = ? " +
                        "AND userID = ?"
                , new String[]{categoria, userID});

        if(cursor.moveToNext()){
            idCategoria = cursor.getInt(cursor.getColumnIndex("idCategoria"));
        }

        return idCategoria;
    }

    //Método para obtener el nombre de la Categoria según el idCategoria
    public String getNameCategoriaById(int idCategoria){
        String categoria = null;

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        Cursor cursor = objDB.rawQuery("SELECT categoria FROM Categorias WHERE idCategoria =?"
                , new String[]{String.valueOf(idCategoria)});

        if(cursor.moveToNext()){
            categoria = cursor.getString(cursor.getColumnIndex("categoria"));
        }

        return categoria;
    }

    public int eliminarCategoria(int idCategoria){
        int resultado = 0;

        // Creamos objeto de la clase Base
        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getWritableDatabase();

        // Consultamos a BD y guardamos en ArrayList
        Cursor cursor = objDB.rawQuery("SELECT * FROM Categorias WHERE idCategoria = ?", new String[]{String.valueOf(idCategoria)});
        if (cursor.moveToNext()) {
            ContentValues values = new ContentValues();
            values.put("estadoCategoria", 2);//El valor "2" en el campo "estadoCategoria" indica "Eliminada"

            //Establecemos como "eliminada" la categoría según su ID
            resultado = objDB.update("Categorias", values, "idCategoria = ?", new String[]{String.valueOf(idCategoria)});
        }

        objDB.close();

        return resultado;
    }
}
