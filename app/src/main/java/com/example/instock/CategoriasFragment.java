package com.example.instock;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.instock.Adapter.CategoriasAdaptador;
import com.example.instock.Adapter.ProductoAdaptadpr;
import com.example.instock.BD.Base;
import com.example.instock.interfaces.RecyclerViewClickInterface;
import com.example.instock.models.ListCategorias;
import com.example.instock.models.ModalDialogValues;
import com.example.instock.models.Producto;
import com.example.instock.utils.CreateDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link // Fragment} subclass.
 * Use the {@link // CategoriasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriasFragment extends Fragment implements RecyclerViewClickInterface {

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

    RecyclerView recyclerCategorias;
    CategoriasAdaptador categoriasAdaptador;
    ArrayList<ListCategorias> CategoriaList;

    //Objeto de MyDialog
    CreateDialog createDialog = new CreateDialog();
    private ModalDialogValues modalDialogValues = ModalDialogValues.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_categorias, container, false);
        btnAgregar = (Button)view.findViewById(R.id.btnAgregar);

        //Cargamos y mostramos los registros de categorías si es que existen
        inicializarElementos();

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
                //EditText cat = (EditText) getView().findViewById(R.id.edtCategoria);
                /*
                List<ListCategorias> categoriasList = new ArrayList<>();
                categoriasList.add(new ListCategorias(cat.getText().toString()));
                categoriasAdaptador = new CategoriasAdaptador(categoriasList, getActivity());
                recyclerCategorias.setAdapter(categoriasAdaptador);*/
                // Creamos la conexion a la BD
                Base obj = new Base(getContext());
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

    private void inicializarElementos() {

        CategoriaList = new ArrayList<>();

        recyclerCategorias = view.findViewById(R.id.rvCategoria);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerCategorias.setLayoutManager(layoutManager);

        // Creamos objeto de la clase Base
        Base obj = new Base(getContext());
        SQLiteDatabase objDB = obj.getWritableDatabase();

        // Creamos Cursor
        Cursor cursor = objDB.rawQuery("SELECT * FROM Categorias",null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            CategoriaList.add(new ListCategorias(cursor.getString(cursor.getColumnIndex("categoria"))));
            cursor.moveToNext();
        }

        categoriasAdaptador = new CategoriasAdaptador(CategoriaList, getActivity(), this);

        recyclerCategorias.setAdapter(categoriasAdaptador);
        objDB.close();
    }

    private void eliminarCategoria(int position){
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
        String consultaEliminar = "DELETE FROM Categorias WHERE idCategoria = " + array_categID.get(position);
        objDB.execSQL(consultaEliminar);

        objDB.close();

        inicializarElementos();//Actualizamos el RecylcerView invocando el método de inicializarElementos()
    }

    // Objeto EditarCategoria
    EditarCategoriasFragment objEditarCategoria = new EditarCategoriasFragment();

    FragmentTransaction transaction;
    Fragment fragmentEditarC;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(int position) {
        Toast.makeText(getContext(), CategoriaList.get(position).getCategorias(), Toast.LENGTH_SHORT).show();

        Drawable drawablePositive = getContext().getDrawable(R.drawable.ic_edit);
        drawablePositive.setTint(Color.parseColor("#0099CC"));
        Drawable drawableNegative = getContext().getDrawable(R.drawable.ic_delete);
        drawableNegative.setTint(Color.parseColor("#FF0000"));

        modalDialogValues.modalDialogValues("Has Seleccionado una Categoría",
                "¿Qué acción deseas hacer?");

        //Invocamos el dialog() y sobreescribimos sus metodos setPositiveButton y setNegativeButton
        createDialog.dialog(getContext()).setPositiveButton(null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //TODO: Llamar método/fragment para editar Categoría

                fragmentEditarC = new EditarCategoriasFragment();
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_view, fragmentEditarC);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }).setPositiveButtonIcon(drawablePositive)
                .setNegativeButton(null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Eliminamos el item del RecyclerView
                CategoriaList.remove(position);
                categoriasAdaptador.notifyDataSetChanged();
                eliminarCategoria(position);//Método para eliminar registro de la BD
                objEditarCategoria.position(position); // Se envia la posición
            }
        }).setNegativeButtonIcon(drawableNegative).show();

    }


}