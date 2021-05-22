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
import android.widget.TextView;

import com.example.instock.Adapter.VentasAdaptador;
import com.example.instock.BD.VentasManagerDB;
import com.example.instock.models.ListaVentas;

import java.util.ArrayList;
import java.util.List;

public class VerVentasFragment extends Fragment {

    VentasAdaptador ventaAdaptador;
    List<ListaVentas> VentasList = new ArrayList<>();
    RecyclerView recyclerVentas;
    RadioButton rdbSemana, rdbMes, rdbAnnio;
    TextView tvTotalVal;

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
        VentasManagerDB ventasManagerDB = new VentasManagerDB(getContext());
        VentasList = ventasManagerDB.obtenerVentas();

        recyclerVentas = getView().findViewById(R.id.recyclerVentas);
        recyclerVentas.setLayoutManager(new LinearLayoutManager(getActivity()));
        ventaAdaptador = new VentasAdaptador(VentasList, getActivity());
        recyclerVentas.setAdapter(ventaAdaptador);

        tvTotalVal.setText("$" + ventasManagerDB.obtenerTotalVentas());
    }

    public void enlazarVistas(View v)
    {
        rdbSemana = v.findViewById(R.id.rdbSemana);
        rdbMes = v.findViewById(R.id.rdbMes);
        rdbAnnio = v.findViewById(R.id.rdbAnnio);
        tvTotalVal = v.findViewById(R.id.tvTotalVal);
    }

}