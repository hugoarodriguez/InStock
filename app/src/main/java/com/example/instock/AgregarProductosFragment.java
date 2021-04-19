package com.example.instock;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;


public class AgregarProductosFragment extends Fragment {

    public AgregarProductosFragment(){}
    ImageView Imagen;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_agregar_productos, container, false);
        Imagen = (ImageView) vista.findViewById(R.id.imgProducto);

        return inflater.inflate(R.layout.fragment_agregar_productos, container, false);
    }

    public void SelectIMG(View view)
    {
        cargarImagen();

    }

    private void cargarImagen() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("Image/");
        Fragment fragment = this;
        startActivityForResult(intent.createChooser(intent,"Seleccione la puta aplicacion"),10);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        Fragment fragment = this;
        this.onActivityResult(requestCode, resultCode, data);
        // super.onActivityResult();
        if(resultCode== RESULT_OK)
        {
            Uri path = data.getData();
            Imagen.setImageURI(path);
        }

    }
}