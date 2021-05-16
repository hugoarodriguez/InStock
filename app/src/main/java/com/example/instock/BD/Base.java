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
        String query = null;
        query = "CREATE TABLE IF NOT EXISTS Categorias(idCategoria INTEGER PRIMARY KEY AUTOINCREMENT, categoria TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = null;
        query = "DROP TABLE IF EXISTS Categorias";
        db.execSQL(query);
        onCreate(db);
    }
}
