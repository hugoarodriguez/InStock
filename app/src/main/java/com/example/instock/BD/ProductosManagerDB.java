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

    //Métodos de AgregarProductosFragment


    //Método para obtener listado de Categorías
    public ArrayList<String> getCategorias(Context context){
        ListCategorias listCategorias;
        ArrayList<String> listCategoriasString = new ArrayList<>();

        String primerValor = context.getResources().getString(R.string.spr_categoria_first_value);
        listCategoriasString.add(primerValor);

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getWritableDatabase();

        // Creamos Cursor
        Cursor cursor = objDB.rawQuery("SELECT * FROM Categorias",null);
        cursor.moveToFirst();
        int index = 0;
        while (cursor.isAfterLast() == false) {
            listCategorias = new ListCategorias(
                    cursor.getInt(cursor.getColumnIndex("idCategoria")),
                    cursor.getString(cursor.getColumnIndex("categoria")));
            listCategoriasString.add(listCategorias.getCategoria());
            cursor.moveToNext();
        }

        return  listCategoriasString;
    }

    //Método para obtener el idCategoria según el nombre de la Categoria
    public int getIDCategoriaByName(Context context, String categoria){
        int idCategoria = 0;

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        Cursor cursor = objDB.rawQuery("SELECT idCategoria FROM Categorias WHERE categoria =?"
                , new String[]{categoria});

        if(cursor.moveToNext()){
            idCategoria = cursor.getInt(cursor.getColumnIndex("idCategoria"));
        }

        return idCategoria;
    }

    //Métotodo para agregar poductos
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

    //Método para obtener el listado de productos
    public ArrayList<Producto> obtenerProductos(Context context){
        Producto producto = new Producto(null, null, null,
                null, null, null, null);
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
                    (cursor.getString(cursor.getColumnIndex("idCatProd"))),
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
        Producto producto = null;

        Base obj = new Base(context);
        SQLiteDatabase objDB = obj.getReadableDatabase();

        // Creamos Cursor
        Cursor cursor = objDB.rawQuery("SELECT * FROM Productos WHERE idProd = ?",new String[]{idProd});

        if (cursor.moveToFirst()) {
            producto = new Producto((cursor.getString(cursor.getColumnIndex("idProd"))),
                    (cursor.getString(cursor.getColumnIndex("nomProd"))),
                    (cursor.getString(cursor.getColumnIndex("idCatProd"))),
                    (cursor.getString(cursor.getColumnIndex("cantProd"))),
                    (cursor.getString(cursor.getColumnIndex("precioProd"))),
                    (cursor.getString(cursor.getColumnIndex("fotoProd"))),
                    (cursor.getString(cursor.getColumnIndex("detalle"))));
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
