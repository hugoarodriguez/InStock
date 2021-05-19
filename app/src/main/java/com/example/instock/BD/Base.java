package com.example.instock.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.instock.CategoriasFragment;

public class Base extends SQLiteOpenHelper {

    public Base(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // Metodo onCreate para crear la Base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCategoria = null;
        String queryCliente = null;
        queryCategoria = "CREATE TABLE IF NOT EXISTS Categorias(idCategoria INTEGER PRIMARY KEY AUTOINCREMENT, categoria TEXT);";
        db.execSQL(queryCategoria);
        queryCliente = "CREATE TABLE IF NOT EXISTS Clientes(idCliente INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, apellido TEXT, sexo TEXT, telefono TEXT, correo TEXT);";
        db.execSQL(queryCliente);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = null;
        query = "DROP TABLE IF EXISTS Categorias";
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS Clientes";
        db.execSQL(query);
        onCreate(db);
    }
}
