package com.example.instock;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.instock.BD.Base;

import java.util.ArrayList;


public class EditarCategoriasFragment extends Fragment {

    Button btnEditar;
    EditText edtEditar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // btnEditar = (Button) getView().findViewById(R.id.btnEditarC);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editar_categorias, container, false);
    }

    int position;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnEditar = (Button) getView().findViewById(R.id.btnEditarC);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Para actualizar los datos
                // Creamos objeto de la clase Base
                Base obj = new Base(getContext());
                SQLiteDatabase objDB = obj.getWritableDatabase();
                // Consultamos a BD y guardamos en ArrayList
                ArrayList<Integer> array_categID = new ArrayList<Integer>();
                Cursor cursor = objDB.rawQuery("SELECT * FROM Categorias",null);
                cursor.moveToFirst();
                while (cursor.isAfterLast() == false) {
                    array_categID.add(cursor.getInt(cursor.getColumnIndex("idCategoria")));
                    cursor.moveToNext();
                }
                //String consultaEliminar = "DELETE FROM Categorias WHERE idCategoria = " + array_categID.get(position);
                //objDB.execSQL(consultaEliminar);

                objDB.close();

                editarCategoria();
                // Para regresar al fragmento CategoriasFragment
                Fragment fragmentCategorias = new CategoriasFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_view, fragmentCategorias);
                transaction.addToBackStack(null);
                transaction.commit();
                Toast.makeText(getContext(), "Posición " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
    ArrayList<Integer> array_categID;
    private void editarCategoria(){
        edtEditar = getView().findViewById(R.id.edtEditarC);
        String nCateg = edtEditar.getText().toString();
        // Creamos objeto de la clase Base
        Base obj = new Base(getContext());
        SQLiteDatabase objDB = obj.getWritableDatabase();
        // Consultamos a BD y guardamos en ArrayList
        array_categID = new ArrayList<Integer>();
        Cursor cursor = objDB.rawQuery("SELECT * FROM Categorias",null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            array_categID.add(cursor.getInt(cursor.getColumnIndex("idCategoria")));
            cursor.moveToNext();
        }
        String consultaActualizar = "UPDATE Categorias SET categoria = '" + nCateg + "' WHERE idCategoria = " + array_categID.get(this.position);
        objDB.execSQL(consultaActualizar);

        objDB.close();
    }

    public void position(int position) {
        this.position = position;
    }
}