package com.example.instock;

import android.content.DialogInterface;
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

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.instock.Adapter.CategoriasAdaptador;
import com.example.instock.BD.CategoriasManagerDB;
import com.example.instock.interfaces.RecyclerViewClickInterface;
import com.example.instock.models.ListCategorias;
import com.example.instock.models.ModalDialogValues;
import com.example.instock.utils.CreateDialog;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class CategoriasFragment extends Fragment implements RecyclerViewClickInterface {

    // Se crea vista
    View view;
    // Se crea boton
    Button btnAgregar;
    EditText etCategoria;
    TextInputLayout tilCategoria;

    RecyclerView recyclerCategorias;
    CategoriasAdaptador categoriasAdaptador;
    ArrayList<ListCategorias> CategoriaList;

    MenuItem btnCancelEdit = null;

    //Objeto de MyDialog
    CreateDialog createDialog = new CreateDialog();
    private ModalDialogValues modalDialogValues = ModalDialogValues.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_categorias, container, false);
        btnAgregar = (Button)view.findViewById(R.id.btnAgregar);
        tilCategoria = (TextInputLayout)view.findViewById(R.id.tilCategoria);
        etCategoria = (EditText)view.findViewById(R.id.edtCategoria);

        //Cargamos y mostramos los registros de categorías si es que existen
        inicializarElementos();
        changeBTNAgregarClickListener(1, 0);//Establecemos el botón como "Agregar"
        etChangedListener();

        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        changeBTNAgregarClickListener(1, 0);
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.categorias_menu, menu);

        btnCancelEdit = menu.findItem(R.id.btnCancelEdit);
        btnCancelEdit.setIcon(R.drawable.ic_baseline_cancel_24);
        btnCancelEdit.setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            //Con este botón cancelamos la edición de la categoría
            case R.id.btnCancelEdit:

                changeBTNAgregarClickListener(1, 0);//Establecemos el botón como "Agregar"

                break;
        }

        return super.onOptionsItemSelected(item);
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(int position) {
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

                //Establecemos el botón como "Editar"
                changeBTNAgregarClickListener(2, position);

            }
        }).setPositiveButtonIcon(drawablePositive)
                .setNegativeButton(null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Eliminamos la Categoría
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

    //Método para enlazar los editText con el ChangedListener
    private void etChangedListener(){
        etCategoria.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0){
                    tilCategoria.setError(null);
                }
                else{
                    tilCategoria.setError("Debe agregar una categoría");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //tipoAccion: 1 - Agregar; 2 - Editar
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeBTNAgregarClickListener(int tipoAccion, int positionItemClicked){

        etCategoria.setText("");
        etCategoria.requestFocus();
        tilCategoria.setError(null);//Quitamos el error si es que se está mostrando

        if(tipoAccion == 1){

            if(btnCancelEdit != null){
                btnCancelEdit.setVisible(false);
            }
            btnAgregar.setText(R.string.btnAgregar);

            //Asignamos la función de Agregar Productos al botón
            btnAgregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String nuevaCateg = etCategoria.getText().toString();

                    if (nuevaCateg.trim().equals("")) {
                        tilCategoria.setError("Debe agregar una categoría");
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
                        etCategoria.setText("");
                        etCategoria.requestFocus();
                        tilCategoria.setError(null);//Quitamos el error si es que se está mostrando
                        inicializarElementos();
                    }
                }
            });

        } else if(tipoAccion == 2){
            String categoria = CategoriaList.get(positionItemClicked).getCategoria();
            etCategoria.setText(categoria);
            Drawable drawable = getResources().getDrawable(R.drawable.ic_baseline_cancel_24);
            drawable.setTint(Color.parseColor("#FFFFFF"));
            btnCancelEdit.setIcon(R.drawable.ic_baseline_cancel_24);
            btnCancelEdit.setVisible(true);
            btnAgregar.setText("Modificar");

            //Asignamos la función de Modificar Productos al botón
            btnAgregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String nombreNuevoCat = etCategoria.getText().toString();
                    int idCategoria = CategoriaList.get(positionItemClicked).getIdCategoria();

                    if (nombreNuevoCat.trim().equals("")) {
                        tilCategoria.setError("Debe agregar una categoría");
                    }
                    else {

                        CategoriasManagerDB categoriasManagerDB = new CategoriasManagerDB(getContext());

                        int resultado = categoriasManagerDB.modificarCategoria(idCategoria, nombreNuevoCat);

                        if(resultado > 0){
                            Toast.makeText(getContext(), "Se modificó el nombre de la categoría",
                                    Toast.LENGTH_SHORT).show();
                        } else if(resultado == -2){
                            Toast.makeText(getContext(), "Ya no existe esa categoría",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "No se ha podido modificar la categoría",
                                    Toast.LENGTH_SHORT).show();
                        }

                        inicializarElementos();
                        changeBTNAgregarClickListener(1, 0);
                    }
                }
            });
        }
    }

}