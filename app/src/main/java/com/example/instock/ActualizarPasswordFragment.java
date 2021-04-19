package com.example.instock;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;


public class ActualizarPasswordFragment extends Fragment {

    ImageButton ibMostrarPasswordA;

    public ActualizarPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_actualizar_password, container, false);

        //Método para ocultar password
        ocultarPassword(v);

        return v;
    }

    public void ocultarPassword(View v){

        /*ibMostrarPasswordA = (ImageButton)v.findViewById(R.id.ibMostrarPasswordA);
        ibMostrarPasswordA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Algo", Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}