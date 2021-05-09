package com.example.instock;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.instock.Adapter.VentasAdaptador;
import java.util.ArrayList;
import java.util.List;

public class VerVentasFragment extends Fragment {

    VentasAdaptador ventaAdaptador;
    List<ListaVentas> VentasList = new ArrayList<>();
    RecyclerView recyclerVentas;
    RadioButton rdbSemana, rdbMes, rdbAnnio;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_ver_ventas, container, false);
        enlazarVistas(vista);
        return vista;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cargarDatos();
    }

    public void cargarDatos()
    {
        VentasList.add(new ListaVentas("Código: 01", "Categoria: Ropa", "Producto: Camiseta", "Cantidad: 1", "Precio: $10.0", "Cliente: María Gutierrez"));
        VentasList.add(new ListaVentas("Código: 02", "Categoria: Accesorios", "Producto: Collar", "Cantidad: 2", "Precio: $5.0", "Cliente: José Gonzales"));
        VentasList.add(new ListaVentas("Código: 03", "Categoria: Zapatos", "Producto: Botas", "Cantidad: 1","Precio: $20.0", "Cliente: Alejandro García"));
        VentasList.add(new ListaVentas("Código: 04", "Categoria: Ropa", "Producto: Vestido","Cantidad: 1", "Precio: $9.0", "Cliente: Lucia Molina"));
        VentasList.add(new ListaVentas("Código: 05", "Categoria: Zapatos", "Producto: Sandalias", "Cantidad: 1", "Precio: $25.0", "Cliente: Monica Alfaro"));

        recyclerVentas = getView().findViewById(R.id.recyclerVentas);
        recyclerVentas.setLayoutManager(new LinearLayoutManager(getActivity()));
        ventaAdaptador = new VentasAdaptador(VentasList, getActivity());
        recyclerVentas.setAdapter(ventaAdaptador);
    }

    public void enlazarVistas(View v)
    {
        rdbSemana = v.findViewById(R.id.rdbSemana);
        rdbMes = v.findViewById(R.id.rdbMes);
        rdbAnnio = v.findViewById(R.id.rdbAnnio);
    }

}