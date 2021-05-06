package com.example.instock;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.instock.Adapter.CategoriasAdaptador;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class AgregarProductosFragment extends Fragment {

    public AgregarProductosFragment(){}
    ImageView imgProducto;
    ImageButton btnSelectImage, btnTakePhoto;
    Button btnAgregar, btnCancelar;
    TextInputLayout tilNombrePro, tilCantPro, tilPrecioPro, tilDetallesPro;
    EditText edtNombrePro, edtCantPro, edtPrecioPro, edtDetallesPro;

    String mensajeAlerta = "Dato requerido";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_agregar_productos, container, false);
        enlazarVistas(vista);//Enlazamos las vistas

        agregar();//Método para btnAgregar
        cancelar();//Método para btnCancelar
        edtChangeListenerAll();//Método para activar la escucha del onChange de todos los EditText

        return vista;
    }

    //Método que carga el estado guardado del Fragment
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        limpiarCampos();
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    //Enlazar vistas
    public void enlazarVistas(View v){
        imgProducto = (ImageView) v.findViewById(R.id.imgProducto);
        btnAgregar = (Button)v.findViewById(R.id.btnAgregar);
        btnCancelar = (Button)v.findViewById(R.id.btnCancelar);
        tilNombrePro = (TextInputLayout)v.findViewById(R.id.tilNombrePro);
        tilCantPro = (TextInputLayout)v.findViewById(R.id.tilCantPro);
        tilDetallesPro = (TextInputLayout)v.findViewById(R.id.tilDetallesPro);
        tilPrecioPro = (TextInputLayout)v.findViewById(R.id.tilPrecioPro);
        edtCantPro = (EditText) v.findViewById(R.id.edtCantPro);
        edtDetallesPro = (EditText)v.findViewById(R.id.edtDetallesPro);
        edtNombrePro = (EditText)v.findViewById(R.id.edtNombrePro);
        edtPrecioPro = (EditText)v.findViewById(R.id.edtPrecioPro);
    }

    //Método para enlazar los editText con el ChangedListener
    private void edtChangeListener(EditText editText, TextInputLayout til){


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0){
                    til.setError(null);
                }
                else{
                    til.setError(mensajeAlerta);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //Método que activa la escucha del onChange de los EditText
    private void edtChangeListenerAll(){
        //Método para quitar alerta en onChanged
        edtChangeListener(edtNombrePro, tilNombrePro);
        edtChangeListener(edtCantPro, tilCantPro);
        edtChangeListener(edtPrecioPro, tilPrecioPro);
        edtChangeListener(edtDetallesPro, tilDetallesPro);
    }

    private void agregar(){

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtNombrePro.getText().toString().equals("")
                        || edtCantPro.getText().toString().equals("")
                        || edtPrecioPro.getText().toString().equals("")
                        || edtDetallesPro.getText().toString().equals("")){

                    if(edtNombrePro.getText().toString().equals("")){
                        tilNombrePro.setError(mensajeAlerta);
                    }
                    if(edtCantPro.getText().toString().equals("")){
                        tilCantPro.setError(mensajeAlerta);
                    }
                    if(edtPrecioPro.getText().toString().equals("")){
                        tilPrecioPro.setError(mensajeAlerta);
                    }
                    if(edtDetallesPro.getText().toString().equals("")){
                        tilDetallesPro.setError(mensajeAlerta);
                    }
                } else {
                    Toast.makeText(getContext(), "¡Agregar!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void cancelar(){
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarCampos();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.productos_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //Objeto que permite seleccionar archivos y por medio del cual se asigna la imagen al objeto imProducto
    ActivityResultLauncher<String> gcSeleccionarImagen = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {

                    if(uri != null){
                        System.out.println(uri.getPath());
                        imgProducto.setImageURI(uri);
                    }
                    else{
                        Toast.makeText(getContext(), "¡No seleccionaste ninguna imagen!", Toast.LENGTH_SHORT).show();
                    }

                }
            });

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.btnSelectImage:

                // Not implemented here
                gcSeleccionarImagen.launch("image/*");

                return false;
            case R.id.btnTakePhoto:

                // Do Fragment menu item stuff here
                return true;

            default:
                break;
        }

        return false;
    }

    private void limpiarCampos(){
        edtNombrePro.setText("");
        edtCantPro.setText("");
        edtPrecioPro.setText("");
        edtDetallesPro.setText("");

        //Ruta de acceso a la imagen "sin_imagen.jpg"
        Uri uri = Uri.parse("android.resource://com.example.instock/drawable/sin_imagen");
        imgProducto.setImageURI(uri);

        tilNombrePro.setError(null);
        tilCantPro.setError(null);
        tilPrecioPro.setError(null);
        tilDetallesPro.setError(null);
    }
}