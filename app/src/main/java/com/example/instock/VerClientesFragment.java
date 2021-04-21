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

import com.example.instock.Adapter.ClientesAdaptador;
import com.example.instock.Adapter.Producto;
import com.example.instock.Adapter.ProductoAdaptadpr;

import java.util.ArrayList;


public class VerClientesFragment extends Fragment {

    RecyclerView recyclerCliente;
    ClientesAdaptador clienteAdaptador;
    ArrayList<ListaClientes> ClientesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ver_clientes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ClientesList = new ArrayList<>();
        RecyclerView recyclerCliente = view.findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerCliente.setLayoutManager(layoutManager);

        clienteAdaptador = new ClientesAdaptador( ClientesList, getActivity());
        recyclerCliente.setAdapter(clienteAdaptador);
        cargarDatos();
    }

    private void cargarDatos() {
        ClientesList.add(new ListaClientes("Nombre: AMILTON ABRAHAM", "Teléfono: 7859-8598","Correo: algo@gmail.com","Sexo: Masculino"));
        ClientesList.add(new ListaClientes("Nombre: AMILTON ABRAHAM", "Teléfono: 7859-8598","Correo: algo@gmail.com","Sexo: Masculino"));
        ClientesList.add(new ListaClientes("Nombre: AMILTON ABRAHAM", "Teléfono: 7859-8598","Correo: algo@gmail.com","Sexo: Masculino"));
        ClientesList.add(new ListaClientes("Nombre: AMILTON ABRAHAM", "Teléfono: 7859-8598","Correo: algo@gmail.com","Sexo: Masculino"));
        ClientesList.add(new ListaClientes("Nombre: AMILTON ABRAHAM", "Teléfono: 7859-8598","Correo: algo@gmail.com","Sexo: Masculino"));
    }



}