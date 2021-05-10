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
import com.example.instock.models.ListaVentas;

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
        VentasList.add(new ListaVentas("01", "Ropa", "Camiseta", "1", "$10.0", "María Gutierrez"));
        VentasList.add(new ListaVentas("02", "Accesorios", "Collar", "2",  "$5.0", "José Gonzales"));
        VentasList.add(new ListaVentas("03", "Zapatos", "Botas", "1","$20.0", "Alejandro García"));
        VentasList.add(new ListaVentas("04", "Ropa", "Vestido","1", "$9.0", "Lucia Molina"));
        VentasList.add(new ListaVentas("05", "Zapatos", "Sandalias", "1", "$25.0", "Monica Alfaro"));

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