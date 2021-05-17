package com.example.instock.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.instock.models.ListCategorias;
import com.example.instock.models.Producto;
import com.example.instock.utils.Utils;

import java.util.ArrayList;

public class ProductosManagerDB {

    //Métodos de AgregarProductosFragment


    //Método para obtener listado de Categorías
    public ArrayList<String> getCategorias(Context context){
        ListCategorias listCategorias = new ListCategorias(null);
        ArrayList<String> listCategoriasString = new ArrayList<String>();
        listCategoriasString.add("Seleccione");

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getWritableDatabase();

        // Creamos Cursor
        Cursor cursor = objDB.rawQuery("SELECT * FROM Categorias",null);
        cursor.moveToFirst();
        int index = 0;
        while (cursor.isAfterLast() == false) {
            listCategorias = new ListCategorias(cursor.getString(cursor.getColumnIndex("categoria")));
            listCategoriasString.add(listCategorias.getCategorias());
            cursor.moveToNext();
        }

        return  listCategoriasString;
    }

    public long agregarProductos(Context context, String nomProd, int cantProd, double precioProd
            , String detalle, String fotoProd, int idCatProd){

        Long resultado;

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nomProd", nomProd);
        values.put("cantProd", cantProd);
        values.put("precioProd", precioProd);
        values.put("detalle", detalle);
        values.put("fotoProd", fotoProd);
        values.put("idCatProd", idCatProd);

        //En la siguiente pasamos el valor "null" para el "Id" ya que este es autoincrementable
        resultado = objDB.insert("Productos", "idProd", values);
        //String query = "INSERT INTO Productos(idProd, nomProd, cant) VALUES ("+null+", '"+ nuevoProducto +"')";

        //objDB.execSQL(query);
        objDB.close();

        return resultado;
    }

    public ArrayList<Producto> obtenerProductos(Context context){
        Producto producto = new Producto(null, null, null, null, null);
        ArrayList<Producto> productos = new ArrayList<>();
        Utils utils = new Utils();

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        // Creamos Cursor
        Cursor cursor = objDB.rawQuery("SELECT * FROM Productos",null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            producto = new Producto((cursor.getString(cursor.getColumnIndex("nomProd"))),
                    (cursor.getString(cursor.getColumnIndex("idCatProd"))),
                    (cursor.getString(cursor.getColumnIndex("cantProd"))),
                    (cursor.getString(cursor.getColumnIndex("precioProd"))),
                    (cursor.getString(cursor.getColumnIndex("fotoProd"))));

            productos.add(producto);
            cursor.moveToNext();
        }

        return productos;
    }
}
