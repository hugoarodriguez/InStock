package com.example.instock;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.instock.Adapter.CategoriasAdaptador;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class AgregarProductosFragment extends Fragment {

    public AgregarProductosFragment(){}
    ImageView imgProducto;
    Button btnImage;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_agregar_productos, container, false);
        imgProducto = (ImageView) vista.findViewById(R.id.imgProducto);
        btnImage = (Button)vista.findViewById(R.id.btnImage);

        //Método para seleccionar imagen
        SelectIMG();

        return vista;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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


    private void SelectIMG()
    {
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abrimos la galería de imágenes
                gcSeleccionarImagen.launch("image/*");
            }
        });
    }
}