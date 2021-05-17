package com.example.instock.BD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.instock.CategoriasFragment;
import com.example.instock.models.ListCategorias;

import java.util.ArrayList;
import java.util.List;

public class Base extends SQLiteOpenHelper {


    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "InStock";

    public Base(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Metodo onCreate para crear la Base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = null;
        query = "CREATE TABLE IF NOT EXISTS Categorias(idCategoria INTEGER PRIMARY KEY AUTOINCREMENT, categoria TEXT);";
        db.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS Productos(idProd INTEGER PRIMARY KEY AUTOINCREMENT, nomProd TEXT, cantProd INTEGER, precioProd REAL, detalle TEXT" +
                ", fotoProd TEXT, idCatProd INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = null;
        query = "DROP TABLE IF EXISTS Categorias";
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS Productos";
        db.execSQL(query);
        onCreate(db);
    }
}
