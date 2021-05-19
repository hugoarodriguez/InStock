package com.example.instock.BD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.instock.R;
import com.example.instock.models.ListCategorias;

import java.util.ArrayList;

public class CategoriasManagerDB {

    //Método para obtener listado de Categorías
    public ArrayList<String> getCategorias(Context context){
        ListCategorias listCategorias;
        ArrayList<String> listCategoriasString = new ArrayList<>();

        String primerValor = context.getResources().getString(R.string.spr_categoria_first_value);
        listCategoriasString.add(primerValor);

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getWritableDatabase();

        // Creamos Cursor
        Cursor cursor = objDB.rawQuery("SELECT * FROM Categorias",null);
        cursor.moveToFirst();
        int index = 0;
        while (cursor.isAfterLast() == false) {
            listCategorias = new ListCategorias(
                    cursor.getInt(cursor.getColumnIndex("idCategoria")),
                    cursor.getString(cursor.getColumnIndex("categoria")));
            listCategoriasString.add(listCategorias.getCategoria());
            cursor.moveToNext();
        }

        return  listCategoriasString;
    }

    //Método para obtener el idCategoria según el nombre de la Categoria
    public int getIDCategoriaByName(Context context, String categoria){
        int idCategoria = 0;

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        Cursor cursor = objDB.rawQuery("SELECT idCategoria FROM Categorias WHERE categoria =?"
                , new String[]{categoria});

        if(cursor.moveToNext()){
            idCategoria = cursor.getInt(cursor.getColumnIndex("idCategoria"));
        }

        return idCategoria;
    }

    //Método para obtener el nombre de la Categoria según el idCategoria
    public String getNameCategoriaById(Context context, int idCategoria){
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

}
