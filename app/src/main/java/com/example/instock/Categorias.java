package com.example.instock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class Categorias extends AppCompatActivity {

    RecyclerView recyclerCategorias;
    CategoriasAdaptador categoriasAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        inicializarElementos(); // Llamamos el metodo para que se inicialice con la aplicación
    }

    private void inicializarElementos() {
        recyclerCategorias = findViewById(R.id.rvCategoria);
        recyclerCategorias.setLayoutManager(new LinearLayoutManager(this));

        List<ListCategorias> categoriasList = new ArrayList<>();
        categoriasList.add(new ListCategorias("Categoría 1"));
        categoriasList.add(new ListCategorias("Categoría 2"));
        categoriasList.add(new ListCategorias("Categoría 3"));
        categoriasList.add(new ListCategorias("Categoría 4"));
        categoriasList.add(new ListCategorias("Categoría 5"));
        categoriasAdaptador = new CategoriasAdaptador(categoriasList, this);

        recyclerCategorias.setAdapter(categoriasAdaptador);
    }

    public void agregar(View view) {
        EditText cat = (EditText) findViewById(R.id.edtCategoria);

        List<ListCategorias> categoriasList = new ArrayList<>();
        categoriasList.add(new ListCategorias(cat.getText().toString()));
        categoriasAdaptador = new CategoriasAdaptador(categoriasList, this);

        recyclerCategorias.setAdapter(categoriasAdaptador);
    }
}