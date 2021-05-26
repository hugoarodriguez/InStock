package com.example.instock.BD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.instock.firebasemanager.FirebaseManager;
import com.example.instock.models.ListaVentas;
import com.example.instock.models.Reserva;
import com.example.instock.utils.Utils;

import java.util.ArrayList;

public class VentasManagerDB {

    Context context;
    String userID;

    public VentasManagerDB(Context context){
        this.context = context;
        FirebaseManager firebaseManager = new FirebaseManager();
        //Obtenemos el id del Usuario que se ha registrado
        userID = firebaseManager.getCurrentUserId();
    }

    //Método para obtener el listado de Reservas (únicamente las que poseen estado Reserva)
    public ArrayList<ListaVentas> obtenerVentas(String Desde, String Hasta){
        Utils utils = new Utils();

        ListaVentas venta;
        ArrayList<ListaVentas> ventas = new ArrayList<>();

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        String query = null;

        // Creamos Cursor
        if (Desde.isEmpty() && Hasta.isEmpty()) {
            query = "SELECT idVenta, Clientes.nombre||' '||Clientes.apellido as nombreCompletoCliente, Categorias.categoria, Productos.nomProd, " +
                    "Reservas.cantProd, Reservas.totalPagar, Productos.fotoProd, Ventas.fechaEntregaFinal " +
                    "FROM Ventas INNER JOIN Reservas ON Reservas.idReserva = Ventas.idReserva " +
                    "INNER JOIN Productos ON Productos.idProd = Reservas.idProd " +
                    "INNER JOIN Categorias ON Categorias.idCategoria = Productos.idCatProd " +
                    "INNER JOIN Clientes ON Clientes.idCliente = Reservas.idCliente WHERE Reservas.userID = ? ORDER by Ventas.idVenta DESC";
        } else {
            query = "SELECT idVenta, Clientes.nombre||' '||Clientes.apellido as nombreCompletoCliente, Categorias.categoria, Productos.nomProd, " +
                    "Reservas.cantProd, Reservas.totalPagar, Productos.fotoProd, Ventas.fechaEntregaFinal " +
                    "FROM Ventas INNER JOIN Reservas ON Reservas.idReserva = Ventas.idReserva " +
                    "INNER JOIN Productos ON Productos.idProd = Reservas.idProd " +
                    "INNER JOIN Categorias ON Categorias.idCategoria = Productos.idCatProd " +
                    "INNER JOIN Clientes ON Clientes.idCliente = Reservas.idCliente WHERE Ventas.fechaEntregaFinal  " +
                    "BETWEEN '"+Desde+"' AND '"+Hasta+"' AND Reservas.userID = ?";
        }

        Cursor cursor = objDB.rawQuery(query, new String[]{userID});
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            String fecha = utils.fromYYYYMMDDtoDDMMYYYY(cursor.getString(cursor.getColumnIndex("fechaEntregaFinal")));

            venta = new ListaVentas(cursor.getString(cursor.getColumnIndex("idVenta")),
                    cursor.getString(cursor.getColumnIndex("categoria")),
                    cursor.getString(cursor.getColumnIndex("nomProd")),
                    cursor.getString(cursor.getColumnIndex("cantProd")),
                    cursor.getString(cursor.getColumnIndex("totalPagar")),
                    cursor.getString(cursor.getColumnIndex("nombreCompletoCliente")),
                    cursor.getString(cursor.getColumnIndex("fotoProd")),
                    fecha);

            ventas.add(venta);
            cursor.moveToNext();
        }

        return ventas;
    }

    //Método para obtener la sumatoria del Total Pagado de todas las ventas (Sin filtros)
    public String obtenerTotalVentas(String Desde, String Hasta){
        String totalVentas = "0";

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        String query = null;

        // Creamos Cursor
        if (Desde.isEmpty() && Hasta.isEmpty()) {
            query = "SELECT SUM(Reservas.totalPagar) as totalVentas " +
                    "FROM Ventas INNER JOIN Reservas ON Reservas.idReserva = Ventas.idReserva " +
                    "WHERE Reservas.userID = ?";
        }
        else
        {
            query = "SELECT SUM(Reservas.totalPagar) as totalVentas " +
                    "FROM Ventas INNER JOIN Reservas ON Reservas.idReserva = Ventas.idReserva WHERE Ventas.fechaEntregaFinal  " +
                    "BETWEEN '"+Desde+"' AND '"+Hasta+"' AND Reservas.userID = ?";
             System.out.println("La fecha es: " + Desde);
             System.out.println("La fecha es: " + Hasta);
        }

        Cursor cursor = objDB.rawQuery(query, new String[]{userID});
        if (cursor.moveToFirst()) {
            totalVentas = cursor.getString(cursor.getColumnIndex("totalVentas"));
        }

        return totalVentas;
    }
}
