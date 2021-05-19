package com.example.instock;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.instock.BD.Base;

public class AgregarClienteFragment extends Fragment {
    // Declaración de variables
    EditText nombre, apellido, telefono, correo;
    Spinner sexo;
    Button agregar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agregar_cliente, container, false);
    }
    int posGenero;
    private void getPosition(int position) {
        this.posGenero = position;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nombre = (EditText)getView().findViewById(R.id.edtNombre);
        apellido = (EditText)getView().findViewById(R.id.edtApellido);
        telefono = (EditText)getView().findViewById(R.id.edtTelefono);
        correo = (EditText)getView().findViewById(R.id.edtCorreo);
        agregar = (Button) getView().findViewById(R.id.btnAgregarC);
        sexo = (Spinner)getView().findViewById(R.id.spinner);
        String[] generos = getResources().getStringArray(R.array.genero);
        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, generos);
        sexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    Toast.makeText(getContext(), "Item seleccionado " + generos[position], Toast.LENGTH_SHORT).show();
                    getPosition(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creamos la conexion a la BD
//                Base obj = new Base(getContext(), "InStock",null,1);
//                SQLiteDatabase objDB = obj.getWritableDatabase();
                String nomb = nombre.getText().toString();
                String ape = apellido.getText().toString();
                String tel = telefono.getText().toString();
                String corr = correo.getText().toString();
                /*String sintaxisSql = "INSERT INTO Clientes VALUES ("+ null + ", '" +  nomb +"', '" + ape + "', '" + generos[posGenero] + "', '" + tel + "', '" + corr + "')";

                if (nomb.trim().equals("") || ape.trim().equals("") || tel.trim().equals("") || corr.trim().equals("")) {
                    agregar.setError("Debe llenar todos los campos");
                }
                else {
                    objDB.execSQL(sintaxisSql);
                    Toast.makeText(getContext(), "Se ha agregado cliente", Toast.LENGTH_SHORT).show();
                    // Limpiamos el EditText
                    nombre.setText("");
                    apellido.setText("");
                    telefono.setText("");
                    correo.setText("");
                    nombre.requestFocus();
                }
                objDB.close();*/
            }
        });
    }

}