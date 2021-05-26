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

        //estadoCategoria: 1 - Activa, 2 - Eliminada
        query = "CREATE TABLE IF NOT EXISTS Categorias(idCategoria INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "categoria TEXT, estadoCategoria INTEGER, userID TEXT);";
        db.execSQL(query);

        //estadoCliente: 1 - Activo, 2 - Eliminado
        query = "CREATE TABLE IF NOT EXISTS Clientes(idCliente INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, apellido TEXT, sexo TEXT, telefono TEXT, correo TEXT, estadoCliente INTEGER, " +
                "userID TEXT);";
        db.execSQL(query);

        //estadoProducto: 1 - Activo, 2 - Eliminado, 3 - Sin stock
        query = "CREATE TABLE IF NOT EXISTS Productos(idProd INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nomProd TEXT, cantProd INTEGER, precioProd REAL, detalle TEXT" +
                ", fotoProd TEXT, idCatProd INTEGER, estadoProducto INTEGER, userID TEXT, " +
                "FOREIGN KEY(idCatProd) REFERENCES Categorias(idCategoria));";
        db.execSQL(query);

        //estadoReserva: 1 - Reserva, 2 - Venta
        query = "CREATE TABLE IF NOT EXISTS " +
                "Reservas(idReserva INTEGER PRIMARY KEY AUTOINCREMENT, idProd INTEGER, idCliente INTEGER, " +
                "cantProd INTEGER, fechaEntregaInicial TEXT, totalPagar Real, estadoReserva INTEGER, " +
                "userID TEXT, " +
                "FOREIGN KEY(idProd) REFERENCES Productos(idProd)," +
                "FOREIGN KEY(idCliente) REFERENCES Clientes(idCliente));";
        db.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS " +
                "Ventas(idVenta INTEGER PRIMARY KEY AUTOINCREMENT, idReserva INTEGER, " +
                "fechaEntregaFinal TEXT, userID TEXT, " +
                "FOREIGN KEY(idReserva) REFERENCES Reservas(idReserva));";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = null;
        query = "DROP TABLE IF EXISTS Categorias";
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS Clientes";
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS Productos";
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS Reservas";
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS Ventas";
        db.execSQL(query);
        onCreate(db);
    }
}
