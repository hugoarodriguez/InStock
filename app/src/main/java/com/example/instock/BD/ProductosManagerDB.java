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

    Context context;

    public ProductosManagerDB(Context context){
        this.context = context;
    }

    //Métotodo para agregar poductos
    public long agregarProducto(String nomProd, int cantProd, double precioProd
            , String detalle, String fotoProd, int idCatProd){

        long resultado;

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nomProd", nomProd);
        values.put("cantProd", cantProd);
        values.put("precioProd", precioProd);
        values.put("detalle", detalle);
        values.put("fotoProd", fotoProd);
        values.put("idCatProd", idCatProd);
        values.put("estadoProducto", 1);//Producto Activo

        //En la siguiente consulta pasamos el valor "null" para el "Id" ya que este es autoincrementable
        resultado = objDB.insert("Productos", "idProd", values);

        //objDB.execSQL(query);
        objDB.close();

        return resultado;
    }

    //Métotodo para modificar poductos
    public int modificarProducto(int idProd, String nomProd, int cantProd, double precioProd
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

    //Método para obtener el listado de Productos (únicamente los activos)
    public ArrayList<Producto> obtenerProductos(){
        CategoriasManagerDB categoriasManagerDB = new CategoriasManagerDB(context);
        Producto producto;
        ArrayList<Producto> productos = new ArrayList<>();
        Utils utils = new Utils();

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        // Creamos Cursor
        Cursor cursor = objDB.rawQuery("SELECT * FROM Productos WHERE estadoProducto = ?",
                new String[]{"1"});
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            producto = new Producto((cursor.getString(cursor.getColumnIndex("idProd"))),
                    (cursor.getString(cursor.getColumnIndex("nomProd"))),
                    categoriasManagerDB.getNameCategoriaById(cursor.getInt(cursor.getColumnIndex("idCatProd"))),
                    (cursor.getString(cursor.getColumnIndex("cantProd"))),
                    (cursor.getString(cursor.getColumnIndex("precioProd"))),
                    (cursor.getString(cursor.getColumnIndex("fotoProd"))),
                    (cursor.getString(cursor.getColumnIndex("detalle"))));

            productos.add(producto);
            cursor.moveToNext();
        }

        return productos;
    }

    //Método para obtener los datos de un Producto (si está activo)
    public Producto obtenerProducto(String idProd){
        CategoriasManagerDB categoriasManagerDB = new CategoriasManagerDB(context);
        Producto producto = null;

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        // Creamos Cursor
        Cursor cursor = objDB.rawQuery("SELECT * FROM Productos WHERE idProd = ? AND estadoProducto = ?",
                new String[]{idProd, "1"});

        if (cursor.moveToFirst()) {
            producto = new Producto(cursor.getString(cursor.getColumnIndex("idProd")),
                    cursor.getString(cursor.getColumnIndex("nomProd")),
                    categoriasManagerDB.getNameCategoriaById(cursor.getInt(cursor.getColumnIndex("idCatProd"))),
                    cursor.getString(cursor.getColumnIndex("cantProd")),
                    cursor.getString(cursor.getColumnIndex("precioProd")),
                    cursor.getString(cursor.getColumnIndex("fotoProd")),
                    cursor.getString(cursor.getColumnIndex("detalle")));
        }

        return producto;
    }

    //Método para obtener la cantidad de existencias de un Producto
    public int obtenerCantProductoById(int idProd){
        int cantProducto = 0;

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        // Creamos Cursor
        Cursor cursor = objDB.rawQuery("SELECT * FROM Productos WHERE idProd = ? AND estadoProducto = ?",
                new String[]{String.valueOf(idProd), "1"});

        if (cursor.moveToFirst()) {
            cantProducto = cursor.getInt(cursor.getColumnIndex("cantProd"));
        }

        return cantProducto;
    }

    //Método para eliminar un Producto
    public int eliminarProducto(int idProd){
        int resultado = 0;

        // Creamos objeto de la clase Base
        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getWritableDatabase();

        Cursor cursor = objDB.rawQuery("SELECT * FROM Productos WHERE idProd = ?", new String[]{String.valueOf(idProd)});
        if (cursor.moveToNext()) {
            ContentValues values = new ContentValues();
            values.put("estadoProducto", 2);//Establecemos el estado del Producto como "Eliminado"

            //Eliminamos el producto según el idProd
            resultado = objDB.update("Productos", values,"idProd = ?", new String[]{String.valueOf(idProd)});
        }

        objDB.close();

        return resultado;
    }
}
