package com.example.instock.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.instock.R;
import com.example.instock.models.ListCategorias;
import com.example.instock.models.Producto;
import com.example.instock.utils.Utils;

import java.util.ArrayList;

public class ProductosManagerDB {

    //Métotodo para agregar poductos
    public long agregarProducto(Context context, String nomProd, int cantProd, double precioProd
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

        //En la siguiente consulta pasamos el valor "null" para el "Id" ya que este es autoincrementable
        resultado = objDB.insert("Productos", "idProd", values);

        //objDB.execSQL(query);
        objDB.close();

        return resultado;
    }

    //Métotodo para modificar poductos
    public int modificarProducto(Context context, int idProd, String nomProd, int cantProd, double precioProd
            , String detalle, String fotoProd, int idCatProd){

        int resultado;

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nomProd", nomProd);
        values.put("cantProd", cantProd);
        values.put("precioProd", precioProd);
        values.put("detalle", detalle);
        values.put("fotoProd", fotoProd);
        values.put("idCatProd", idCatProd);

        //Actualizamos el producto
        resultado = objDB.update("Productos", values, "idProd = ?", new String[]{String.valueOf(idProd)});

        objDB.close();

        return resultado;
    }

    //Método para obtener el listado de productos
    public ArrayList<Producto> obtenerProductos(Context context){
        CategoriasManagerDB categoriasManagerDB = new CategoriasManagerDB();
        Producto producto;
        ArrayList<Producto> productos = new ArrayList<>();
        Utils utils = new Utils();

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        // Creamos Cursor
        Cursor cursor = objDB.rawQuery("SELECT * FROM Productos",null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            producto = new Producto((cursor.getString(cursor.getColumnIndex("idProd"))),
                    (cursor.getString(cursor.getColumnIndex("nomProd"))),
                    categoriasManagerDB.getNameCategoriaById(context, cursor.getInt(cursor.getColumnIndex("idCatProd"))),
                    (cursor.getString(cursor.getColumnIndex("cantProd"))),
                    (cursor.getString(cursor.getColumnIndex("precioProd"))),
                    (cursor.getString(cursor.getColumnIndex("fotoProd"))),
                    (cursor.getString(cursor.getColumnIndex("detalle"))));

            productos.add(producto);
            cursor.moveToNext();
        }

        return productos;
    }

    //Método para obtener los datos de un Producto
    public Producto obtenerProducto(Context context, String idProd){
        CategoriasManagerDB categoriasManagerDB = new CategoriasManagerDB();
        Producto producto = null;

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        // Creamos Cursor
        Cursor cursor = objDB.rawQuery("SELECT * FROM Productos WHERE idProd = ?",new String[]{idProd});

        if (cursor.moveToFirst()) {
            producto = new Producto(cursor.getString(cursor.getColumnIndex("idProd")),
                    cursor.getString(cursor.getColumnIndex("nomProd")),
                    categoriasManagerDB.getNameCategoriaById(context, cursor.getInt(cursor.getColumnIndex("idCatProd"))),
                    cursor.getString(cursor.getColumnIndex("cantProd")),
                    cursor.getString(cursor.getColumnIndex("precioProd")),
                    cursor.getString(cursor.getColumnIndex("fotoProd")),
                    cursor.getString(cursor.getColumnIndex("detalle")));
        }

        return producto;
    }

    //Método para eliminar un Producto
    public long eliminarProducto(Context context, int idProd){
        long resultado = 0l;

        // Creamos objeto de la clase Base
        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getWritableDatabase();
        // Consultamos a BD y guardamos en ArrayList
        ArrayList<Integer> array_categID = new ArrayList<>();
        Cursor cursor = objDB.rawQuery("SELECT * FROM Productos WHERE idProd = ?", new String[]{String.valueOf(idProd)});
        if (cursor.moveToNext()) {
            //Eliminamos el producto según el idProd
            resultado = objDB.delete("Productos", "idProd = ?", new String[]{String.valueOf(idProd)});
        }

        objDB.close();

        return resultado;
    }
}
