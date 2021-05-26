package com.example.instock;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instock.Adapter.ClientesAdaptador;
import com.example.instock.Adapter.DesarrolladorAdapter;
import com.example.instock.Adapter.ReservasAdaptador;
import com.example.instock.models.ListaDesarrolladores;
import com.example.instock.models.Reserva;

import java.util.ArrayList;
import java.util.List;


public class AcercaDe extends Fragment {


    RecyclerView recyclerDev;
    DesarrolladorAdapter devAdaptador;
    List<ListaDesarrolladores> devList;
    ListaDesarrolladores devs;
    View vista;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_acerca_de, container, false);
        cargarDatos();
        return vista;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void cargarDatos()
    {
        devList = new ArrayList<>();
        devList.add(new ListaDesarrolladores("#edcae6","Alvarado Henríquez, Sofía Michelle", "25-3152-2017"));
        devList.add(new ListaDesarrolladores("#cadeed","Hernández Campos, Samuel Enrique", "25-3836-2017"));
        devList.add(new ListaDesarrolladores("#caede1","Palma Portillo, Bladimir Stanley", "25-0369-2017"));
        devList.add(new ListaDesarrolladores("#eaedca","Rodríguez Cruz, Hugo Alexander", "25-0663-2017"));
        devList.add(new ListaDesarrolladores("#eddcca","Rodríquez Sigüenza, Amilton Abraham", "25-0259-2017"));

        recyclerDev = vista.findViewById(R.id.rcDev);
        recyclerDev.setLayoutManager(new LinearLayoutManager(getActivity()));

        devAdaptador = new DesarrolladorAdapter(devList, getActivity());
        recyclerDev.setAdapter(devAdaptador);

    }
}