package com.example.instock;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ModificarClienteFragment extends Fragment {

    ModificarClienteFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        ((MainMenu)getActivity()).setFragmentActivo(true);//Indicamos que hay un Framgent Activo
        ((MainMenu)getActivity()).displayBackArrowOrHamburger(getContext(), 1);//Invocamos el método
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modificar_cliente, container, false);
    }
}