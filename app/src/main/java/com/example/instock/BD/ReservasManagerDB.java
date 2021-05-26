package com.example.instock.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.instock.models.ListaClientes;
import com.example.instock.models.Reserva;
import com.example.instock.utils.Utils;

import java.util.ArrayList;
import java.util.Date;

public class ReservasManagerDB {

    Context context;

    public ReservasManagerDB(Context context){
        this.context = context;
    }

    public long agregarReserva(int idProd, int idCliente, int cantProdActual, int cantProdReservado,
                               double precioProd, String fechaEntregaInicial){
        long resultado = 0l;

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("idProd", idProd);
        values.put("idCliente", idCliente);
        values.put("cantProd", cantProdReservado);//Cantidad de producto a reservar
        values.put("fechaEntregaInicial", fechaEntregaInicial);//Fecha en que se debe entregar el producto
        values.put("totalPagar", (precioProd*cantProdReservado));//Total a pagar por el producto
        values.put("estadoReserva", 1);//Indica que aún es una reserva y no se concretó su venta

        //En la siguiente consulta pasamos el valor "null" para el "Id" ya que este es autoincrementable
        resultado = objDB.insert("Reservas", "idReserva", values);

        if(resultado != -1){
            int cantProductoFinal = cantProdActual - cantProdReservado;
            ContentValues updateValues = new ContentValues();
            updateValues.put("cantProd", cantProductoFinal);

            //Actualizamos el stock del producto reservado
            objDB.update("Productos", updateValues, "idProd = ?",
                    new String[]{String.valueOf(idProd)});
        }

        objDB.close();

        return  resultado;
    }

    //Método para obtener el listado de Reservas (únicamente las que poseen estado Reserva)
    public ArrayList<Reserva> obtenerReservas(){
        Utils utils = new Utils();

        Reserva reserva;
        ArrayList<Reserva> reservas = new ArrayList<>();

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        // Creamos Cursor
        String query = "SELECT idReserva, nombre||' '||apellido as nombreCompleto, nomProd, Reservas.cantProd, " +
                "totalPagar, fotoProd, fechaEntregaInicial " +
                "FROM Reservas INNER JOIN Productos ON Productos.idProd = Reservas.idProd " +
                "INNER JOIN Clientes ON Clientes.idCliente = Reservas.idCliente " +
                "WHERE estadoReserva = ? ORDER BY DATE(fechaEntregaInicial)";
        Cursor cursor = objDB.rawQuery(query,new String[]{"1"});
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            String fecha = utils.fromYYYYMMDDtoDDMMYYYY(cursor.getString(cursor.getColumnIndex("fechaEntregaInicial")));

            reserva = new Reserva(cursor.getString(cursor.getColumnIndex("idReserva")),
                    cursor.getString(cursor.getColumnIndex("nomProd")),
                    cursor.getString(cursor.getColumnIndex("totalPagar")),
                    cursor.getString(cursor.getColumnIndex("nombreCompleto")),
                    cursor.getString(cursor.getColumnIndex("fotoProd")),
                    cursor.getString(cursor.getColumnIndex("cantProd")),
                    fecha);

            reservas.add(reserva);
            cursor.moveToNext();
        }

        return reservas;
    }

    //Método para obtener el listado de Reservas (únicamente las que poseen estado Reserva)
    public ArrayList<Reserva> obtenerReservasLike(String correo){
        Utils utils = new Utils();

        Reserva reserva;
        ArrayList<Reserva> reservas = new ArrayList<>();

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        // Creamos Cursor
        String query = "SELECT idReserva, Clientes.correo, nomProd, Reservas.cantProd, " +
                "totalPagar, fotoProd, fechaEntregaInicial " +
                "FROM Reservas INNER JOIN Productos ON Productos.idProd = Reservas.idProd " +
                "INNER JOIN Clientes ON Clientes.idCliente = Reservas.idCliente " +
                "WHERE estadoReserva = ? AND Clientes.correo LIKE ? ORDER BY DATE(fechaEntregaInicial)";
        Cursor cursor = objDB.rawQuery(query,new String[]{"1", correo+"%"});
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            String fecha = utils.fromYYYYMMDDtoDDMMYYYY(cursor.getString(cursor.getColumnIndex("fechaEntregaInicial")));

            reserva = new Reserva(cursor.getString(cursor.getColumnIndex("idReserva")),
                    cursor.getString(cursor.getColumnIndex("nomProd")),
                    cursor.getString(cursor.getColumnIndex("totalPagar")),
                    cursor.getString(cursor.getColumnIndex("correo")),
                    cursor.getString(cursor.getColumnIndex("fotoProd")),
                    cursor.getString(cursor.getColumnIndex("cantProd")),
                    fecha);

            reservas.add(reserva);
            cursor.moveToNext();
        }

        return reservas;
    }

    public int getIdProdByIdReserva(int idReserva){
        int idProd = 0;

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        Cursor cursor = objDB.rawQuery("SELECT idProd FROM Reservas WHERE idReserva = ?",
                new String[]{String.valueOf(idReserva)});
        if(cursor.moveToFirst()){
            idProd = cursor.getInt(cursor.getColumnIndex("idProd"));
        }

        return idProd;
    }

    //Método para convertir una Reserva en Venta
    public int convertirReservaEnVenta(int idReserva, String fechaEntregaFinal){
        int resultado = 0;

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getWritableDatabase();

        Cursor cursor = objDB.rawQuery("SELECT * FROM Reservas WHERE idReserva = ? AND estadoReserva = ?",
                new String[]{String.valueOf(idReserva), "1"});
        if(cursor.moveToFirst()){
            long resultadoInsertVenta = 0;

            //Registramos la Venta
            ContentValues values = new ContentValues();
            values.put("idReserva", idReserva);
            values.put("fechaEntregaFinal", fechaEntregaFinal);
            resultadoInsertVenta = objDB.insert("Ventas", "idVenta", values);

            //Vetificamos que se haya insertado la Venta
            if(resultadoInsertVenta > 0){
                //Establecemos el estado de la Reserva como "2" que significa "Venta"
                ContentValues valuesUpdate = new ContentValues();
                valuesUpdate.put("estadoReserva", 2);

                resultado = objDB.update("Reservas", valuesUpdate, "idReserva = ?",
                        new String[]{String.valueOf(idReserva)});
            }
        }

        return resultado;
    }

    //Método para cancelar una Reserva
    public int cancelarReserva(int idReserva){
        int resultado = 0;

        //Obtenemos el "idProd" de la Reserva
        int idProd = getIdProdByIdReserva(idReserva);

        //Obtenemos la cantidad de producto disponibles para reservar
        ProductosManagerDB productosManagerDB = new ProductosManagerDB(context);
        int cantExistencias = productosManagerDB.obtenerCantProductoById(idProd);

        // Creamos objeto de la clase Base
        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getWritableDatabase();

        Cursor cursor = objDB.rawQuery("SELECT * FROM Reservas WHERE idReserva = ? AND estadoReserva = ?",
                new String[]{String.valueOf(idReserva), "1"});
        if (cursor.moveToNext()) {
            int cantReservados = cursor.getInt(cursor.getColumnIndex("cantProd"));
            cantExistencias = cantExistencias + cantReservados;//Agregamos los productos reservados a las existencias

            //Actualizamos las existencias del producto
            ContentValues values = new ContentValues();
            values.put("cantProd", cantExistencias);

            resultado = objDB.update("Productos", values,"idProd = ?", new String[]{String.valueOf(idProd)});

            //Si se actualizaron las existencias del Producto, eliminamos la reserva
            if(resultado > 0){
                resultado = objDB.delete("Reservas", "idReserva = ?", new String[]{String.valueOf(idReserva)});
            }
        }

        objDB.close();

        return resultado;
    }
}
