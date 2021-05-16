package com.example.instock;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.instock.Adapter.CategoriasAdaptador;
import com.example.instock.BD.Base;
import com.example.instock.models.ListCategorias;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link // Fragment} subclass.
 * Use the {@link // CategoriasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    /*private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";*/

    // TODO: Rename and change types of parameters
    /*private String mParam1;
    private String mParam2;*/

    /*public CategoriasFragment() {
        // Required empty public constructor
    }*/

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param //param1 Parameter 1.
     * @param //param2 Parameter 2.
     * @return A new instance of fragment CategoriasFragment.
     */
    // TODO: Rename and change types and number of parameters
    /*public static CategoriasFragment newInstance(String param1, String param2) {
        CategoriasFragment fragment = new CategoriasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }*/

    // Se crea vista
    View view;
    // Se crea boton
    Button btnAgregar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_categorias, container, false);
        btnAgregar = (Button)view.findViewById(R.id.btnAgregar);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inicializarElementos();
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*EditText cat = (EditText) getView().findViewById(R.id.edtCategoria);

                List<ListCategorias> categoriasList = new ArrayList<>();
                categoriasList.add(new ListCategorias(cat.getText().toString()));
                categoriasAdaptador = new CategoriasAdaptador(categoriasList, getActivity());
                recyclerCategorias.setAdapter(categoriasAdaptador);*/
                // Creamos la conexion a la BD
                Base obj = new Base(getContext(), "InStock",null,1);
                SQLiteDatabase objDB = obj.getWritableDatabase();

                EditText categ = (EditText) getView().findViewById(R.id.edtCategoria);
                String nuevaCateg = categ.getText().toString();
                String sintaxisSql = "INSERT INTO Categorias VALUES ("+ null + ", '" +  nuevaCateg +"')";

                if (nuevaCateg.trim().equals("")) {
                    categ.setError("Debe agregar un producto");
                }
                else {
                    objDB.execSQL(sintaxisSql);
                    Toast.makeText(getContext(), "Se ha agregado categoria", Toast.LENGTH_SHORT).show();
                    // Limpiamos el EditText
                    categ.setText("");
                    categ.requestFocus();
                    inicializarElementos();
                }
                objDB.close();
            }
        });
    }

    RecyclerView recyclerCategorias;
    CategoriasAdaptador categoriasAdaptador;
    // ArrayList para recibir datos de BD
    ArrayList<String> array_list;
    private void inicializarElementos() {
        recyclerCategorias = getView().findViewById(R.id.rvCategoria);
        recyclerCategorias.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Creamos objeto de la clase Base
        Base obj = new Base(getContext(), "InStock",null,1);
        SQLiteDatabase objDB = obj.getWritableDatabase();
        array_list = new ArrayList<String>();
        List<ListCategorias> categoriasList = new ArrayList<>();
        // Creamos Cursor
        Cursor cursor = objDB.rawQuery("SELECT * FROM Categorias",null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            categoriasList.add(new ListCategorias(cursor.getString(cursor.getColumnIndex("categoria"))));
            cursor.moveToNext();
        }
        /*categoriasList.add(new ListCategorias("Categoría 1"));
        categoriasList.add(new ListCategorias("Categoría 2"));
        categoriasList.add(new ListCategorias("Categoría 3"));
        categoriasList.add(new ListCategorias("Categoría 4"));
        categoriasList.add(new ListCategorias("Categoría 5"));*/
        categoriasAdaptador = new CategoriasAdaptador(categoriasList, getActivity());

        recyclerCategorias.setAdapter(categoriasAdaptador);


    }
}