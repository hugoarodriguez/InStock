package com.example.instock;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instock.Adapter.Producto;
import com.example.instock.Adapter.ProductoAdaptadpr;

import java.util.ArrayList;


public class ConsultaProductosFragment extends Fragment {

    RecyclerView recyclerProducto;
    ProductoAdaptadpr productoAdaptador;
    ArrayList<Producto> ProductoList;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_consulta_productos, container, false);

        return vista;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ProductoList = new ArrayList<>();
        RecyclerView recyclerProducto =view.findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerProducto.setLayoutManager(layoutManager);


        productoAdaptador = new ProductoAdaptadpr( ProductoList, getActivity());
        recyclerProducto.setAdapter(productoAdaptador);
        cargarDatos();
    }


    private void cargarDatos() {
        {
            for (int i =0; i<100;i++) {
                ProductoList.add(new Producto("Producto: Camisa verde", "Categoria: Camisas"));
            }



        }

    }
}