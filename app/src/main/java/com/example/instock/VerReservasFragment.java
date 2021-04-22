package com.example.instock;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instock.Adapter.Producto;
import com.example.instock.Adapter.ProductoAdaptadpr;

import java.util.ArrayList;


public class VerReservasFragment extends Fragment {

    RecyclerView recyclerReserva;
    ProductoAdaptadpr ReservaAdaptador;
    ArrayList<Producto> ReservaList;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_ver_reservas, container, false);

        return vista;
    }
}