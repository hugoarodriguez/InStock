package com.example.instock;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class InicioFragment extends Fragment {

    View vista;
    TextView tvSaludo;

    public InicioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_inicio, container, false);
        // Inflate the layout for this fragment
        return vista;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        try {
            String nombreUsuario = getArguments().getString("nombreUsuario");

            tvSaludo = (TextView)vista.findViewById(R.id.tvSaludo);
            tvSaludo.setText("¡Hola " + nombreUsuario + "!\nBienvenido a InStock");
        }catch (Exception e){

        }

        super.onViewStateRestored(savedInstanceState);
    }
}