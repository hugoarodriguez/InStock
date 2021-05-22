package com.example.instock.BD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.instock.models.ListaVentas;
import com.example.instock.models.Reserva;
import com.example.instock.utils.Utils;

import java.util.ArrayList;

public class VentasManagerDB {

    Context context;

    public VentasManagerDB(Context context){ this.context = context; }

    //Método para obtener el listado de Reservas (únicamente las que poseen estado Reserva)
    public ArrayList<ListaVentas> obtenerVentas(){
        Utils utils = new Utils();

        ListaVentas venta;
        ArrayList<ListaVentas> ventas = new ArrayList<>();

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        // Creamos Cursor
        String query = "SELECT idVenta, Clientes.nombre||' '||Clientes.apellido as nombreCompletoCliente, Categorias.categoria, Productos.nomProd, " +
                "Reservas.cantProd, Reservas.totalPagar, Productos.fotoProd " +
                "FROM Ventas INNER JOIN Reservas ON Reservas.idReserva = Ventas.idReserva " +
                "INNER JOIN Productos ON Productos.idProd = Reservas.idProd " +
                "INNER JOIN Categorias ON Categorias.idCategoria = Productos.idCatProd " +
                "INNER JOIN Clientes ON Clientes.idCliente = Reservas.idCliente";
        Cursor cursor = objDB.rawQuery(query, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            venta = new ListaVentas(cursor.getString(cursor.getColumnIndex("idVenta")),
                    cursor.getString(cursor.getColumnIndex("categoria")),
                    cursor.getString(cursor.getColumnIndex("nomProd")),
                    cursor.getString(cursor.getColumnIndex("cantProd")),
                    cursor.getString(cursor.getColumnIndex("totalPagar")),
                    cursor.getString(cursor.getColumnIndex("nombreCompletoCliente")),
                    cursor.getString(cursor.getColumnIndex("fotoProd")));

            ventas.add(venta);
            cursor.moveToNext();
        }

        return ventas;
    }

    //Método para obtener la sumatoria del Total Pagado de todas las ventas (Sin filtros)
    public String obtenerTotalVentas(){
        String totalVentas = "0";

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        // Creamos Cursor
        String query = "SELECT SUM(Reservas.totalPagar) as totalVentas " +
                "FROM Ventas INNER JOIN Reservas ON Reservas.idReserva = Ventas.idReserva";
        Cursor cursor = objDB.rawQuery(query, null);
        if(cursor.moveToFirst()){
            totalVentas = cursor.getString(cursor.getColumnIndex("totalVentas"));
        }

        return totalVentas;
    }
}
