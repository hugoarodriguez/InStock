package com.example.instock;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.instock.Adapter.Producto;
import com.example.instock.Adapter.ProductoAdaptadpr;

import java.util.ArrayList;


public class ConsultaProductosFragment extends Fragment {

    RecyclerView recyclerProducto;
    ProductoAdaptadpr productoAdaptador;
    ArrayList<Producto> ProductoList;
    Button btnm;





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
        btnm = view.findViewById(R.id.btnModifica);
        RecyclerView recyclerProducto =view.findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerProducto.setLayoutManager(layoutManager);

        cargarDatos();
        productoAdaptador = new ProductoAdaptadpr( ProductoList, getActivity());

        recyclerProducto.setAdapter(productoAdaptador);

    }


    private void cargarDatos() {

                ProductoList.add(new Producto("Producto: Camisa verde", "Categoria: Camisas","Cantidad: 25 Unidades","Precio $13.50"));
                ProductoList.add(new Producto("Producto: Short Azul", "Categoria: Short","Cantidad: 25 Unidades","Precio $8.50"));
                ProductoList.add(new Producto("Producto: Zapatos de vestir", "Categoria: Zapatos","Cantidad: 12 Unidades","Precio $25.75"));
                ProductoList.add(new Producto("Producto: Collar", "Categoria: Joyeria","Cantidad: 5 Unidades","Precio $2.50"));
                ProductoList.add(new Producto("Producto: Audifonos", "Categoria: Auriculares","Cantidad: 12 Unidades","Precio 7.00"));
    }
}