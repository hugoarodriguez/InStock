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
import android.widget.Toast;

import com.example.instock.Adapter.CategoriasAdaptador;
import com.example.instock.BD.Base;
import com.example.instock.BD.CategoriasManagerDB;
import com.example.instock.interfaces.RecyclerViewClickInterface;
import com.example.instock.models.ListCategorias;
import com.example.instock.models.ModalDialogValues;
import com.example.instock.utils.CreateDialog;

import java.util.ArrayList;

public class CategoriasFragment extends Fragment implements RecyclerViewClickInterface {

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

                EditText categ = (EditText) getView().findViewById(R.id.edtCategoria);
                String nuevaCateg = categ.getText().toString();

                if (nuevaCateg.trim().equals("")) {
                    categ.setError("Debe agregar un producto");
                }
                else {

                    CategoriasManagerDB categoriasManagerDB = new CategoriasManagerDB(getContext());

                    long resultado = categoriasManagerDB.agregarCategoria(nuevaCateg);

                    if(resultado > 0){
                        Toast.makeText(getContext(), "Se guardó la categoría",
                                Toast.LENGTH_SHORT).show();
                    } else if(resultado == -2){
                        Toast.makeText(getContext(), "Ya existe una categoría con ese nombre",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "No se ha podido guardar la categoría",
                                Toast.LENGTH_SHORT).show();
                    }
                    categ.setText("");
                    categ.requestFocus();
                    inicializarElementos();
                }
            }
        });
    }

    private void inicializarElementos() {

        CategoriasManagerDB categoriasManagerDB = new CategoriasManagerDB(getContext());

        CategoriaList = categoriasManagerDB.getCategorias();

        recyclerCategorias = view.findViewById(R.id.rvCategoria);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerCategorias.setLayoutManager(layoutManager);

        categoriasAdaptador = new CategoriasAdaptador(CategoriaList, getActivity(), this);

        recyclerCategorias.setAdapter(categoriasAdaptador);
    }

    // Objeto EditarCategoria
    EditarCategoriasFragment objEditarCategoria = new EditarCategoriasFragment();

    FragmentTransaction transaction;
    Fragment fragmentEditarC;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(int position) {
        Toast.makeText(getContext(), CategoriaList.get(position).getCategoria(), Toast.LENGTH_SHORT).show();

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
                transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_view, fragmentEditarC);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }).setPositiveButtonIcon(drawablePositive)
                .setNegativeButton(null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                CategoriasManagerDB categoriasManagerDB = new CategoriasManagerDB(getContext());

                int idCategoria = CategoriaList.get(position).getIdCategoria();
                int resultado = categoriasManagerDB.eliminarCategoria(idCategoria);

                if(resultado > 0){
                    //Eliminamos el item del RecyclerView
                    CategoriaList.remove(position);
                    categoriasAdaptador.notifyDataSetChanged();
                    inicializarElementos();//Actualizamos el RecylcerView
                }
            }
        }).setNegativeButtonIcon(drawableNegative).show();

    }


}