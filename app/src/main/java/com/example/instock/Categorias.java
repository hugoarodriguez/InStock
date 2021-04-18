package com.example.instock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class Categorias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);
    }

    public void agregar(View view) {
        EditText cat = (EditText) findViewById(R.id.edtCategoria);

        /*List<ListCategorias> categoriasList = new ArrayList<>();
        categoriasList.add(new ListCategorias(cat.getText().toString()));
        categoriasAdaptador = new CategoriasAdaptador(categoriasList, this);

        recyclerCategorias.setAdapter(categoriasAdaptador);*/
    }

    private void arreglo() {
        ArrayList categ = new ArrayList();
        categ.add("");
    }
}