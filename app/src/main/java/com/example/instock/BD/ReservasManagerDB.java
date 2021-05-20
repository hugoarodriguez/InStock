package com.example.instock.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.instock.models.ListaClientes;
import com.example.instock.models.Reserva;

import java.util.ArrayList;

public class ReservasManagerDB {

    Context context;

    public ReservasManagerDB(Context context){
        this.context = context;
    }

    public long agregarReserva(int idProd, int idCliente, int cantProdActual, int cantProdReservado,
                               double precioProd){
        long resultado = 0l;

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("idProd", idProd);
        values.put("idCliente", idCliente);
        values.put("cantProd", cantProdReservado);//Cantidad de producto a reservar
        values.put("totalPagar", (precioProd*cantProdReservado));//Total a pagar por el producto

        //En la siguiente consulta pasamos el valor "null" para el "Id" ya que este es autoincrementable
        resultado = objDB.insert("Reservas", "idReserva", values);

        if(resultado != -1){
            //TODO: actualizar stock del producto reservado
            int cantProductoFinal = cantProdActual - cantProdReservado;
            ContentValues updateValues = new ContentValues();
            updateValues.put("cantProd", cantProductoFinal);

            //Actualizamos el stock del producto reservado
            objDB.update("Productos", updateValues, "idProd = ?",
                    new String[]{String.valueOf(idProd)});
        }

        //objDB.execSQL(query);
        objDB.close();

        return  resultado;
    }

    //Método para obtener el listado de productos
    public ArrayList<Reserva> obtenerReservas(){
        Reserva reserva;
        ArrayList<Reserva> reservas = new ArrayList<>();

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        // Creamos Cursor
        String query = "SELECT nombre||' '||apellido as nombreCompleto, nomProd, Reservas.cantProd, totalPagar, fotoProd " +
                "FROM Reservas INNER JOIN Productos ON Productos.idProd = Reservas.idProd INNER JOIN Clientes ON Clientes.idCliente = Reservas.idCliente";
        Cursor cursor = objDB.rawQuery(query,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            reserva = new Reserva(cursor.getString(cursor.getColumnIndex("nomProd")),
                    cursor.getString(cursor.getColumnIndex("totalPagar")),
                    cursor.getString(cursor.getColumnIndex("nombreCompleto")),
                    cursor.getString(cursor.getColumnIndex("fotoProd")),
                    cursor.getString(cursor.getColumnIndex("cantProd")));

            reservas.add(reserva);
            cursor.moveToNext();
        }

        return reservas;
    }
}
