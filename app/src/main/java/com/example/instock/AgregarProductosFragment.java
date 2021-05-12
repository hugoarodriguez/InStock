package com.example.instock;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;

import com.example.instock.models.ModalDialogValues;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.example.instock.utils.*;

import static android.app.Activity.RESULT_OK;


public class AgregarProductosFragment extends Fragment {

    public AgregarProductosFragment(){}
    ImageView imgProducto;
    ImageButton btnSelectImage, btnTakePhoto;
    Button btnAgregar, btnCancelar;
    TextInputLayout tilNombrePro, tilCantPro, tilPrecioPro, tilDetallesPro;
    EditText edtNombrePro, edtCantPro, edtPrecioPro, edtDetallesPro;

    String mensajeAlerta = "Dato requerido";

    static final int REQUEST_TAKE_PHOTO = 1;//Variable para tomar foto

    File photoFile = null;
    Uri photoURI = null;
    Utils utils = new Utils();
    int tipoIntent = 0; //1 - Seleccionar Imagen, 2 - Tomar Foto

    //Objeto de MyDialog
    CreateDialog createDialog = new CreateDialog();
    private ModalDialogValues modalDialogValues = ModalDialogValues.getInstance();

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
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

                    //Asignamos los valores para mostrar el Dialog
                    modalDialogValues.modalDialogValues("Agregar Producto",
                            "¿Estás seguro que deseas agregar este producto?");

                    //Invocamos el dialog() y sobreescribimos sus metodos setPositiveButton y setNegativeButton
                    createDialog.dialog(getContext()).setPositiveButton(null, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Toast.makeText(getContext(), "¡Agregar!", Toast.LENGTH_SHORT).show();

                        }
                    }).setNegativeButton(null, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
                }
            }
        });
    }

    private void cancelar(){
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                //Asignamos los valores para mostrar el Dialog
                modalDialogValues.modalDialogValues("Limpiar",
                        "Se limpiarán los datos ingresados ¿Estás seguro?");

                //Invocamos el dialog() y sobreescribimos sus metodos setPositiveButton y setNegativeButton
                createDialog.dialog(getContext()).setPositiveButton(null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        limpiarCampos();

                    }
                }).setNegativeButton(null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.productos_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /*Objeto que permite obtener manipular los resultados de los intents de Seleccionar Imagen
    o de Tomar Foto*/
    private ActivityResultLauncher<Intent> gcObtenerFoto = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    // Handle the Intent
                    //Toast.makeText(getContext(), "¡Foto Guardada!", Toast.LENGTH_SHORT).show();

                    if(tipoIntent == 1){
                        Uri uri = result.getData().getData();
                        InputStream is = null;
                        try {
                            is = getActivity().getContentResolver().openInputStream(uri);

                            Bitmap bitmap = BitmapFactory.decodeStream(is);
                            int rotation = utils.getRotationFromGallery(getContext(), uri);
                            int rotationInDegrees = utils.exifToDegrees(rotation);

                            Bitmap imagenFinal = utils.rotateAndBitmapConvert(rotation, rotationInDegrees, bitmap);
                            imgProducto.setImageBitmap(imagenFinal);

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                    } else if(tipoIntent == 2){
                        Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsoluteFile().getAbsolutePath());
                        ExifInterface exif = null;
                        try {
                            exif = new ExifInterface(photoFile.getAbsoluteFile().getAbsolutePath());
                            int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                            int rotationInDegrees = utils.exifToDegrees(rotation);

                            Bitmap imagenFinal = utils.rotateAndBitmapConvert(rotation, rotationInDegrees, bitmap);
                            imgProducto.setImageBitmap(imagenFinal);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //bitmap.recycle();
                    }
                } else{
                    if(tipoIntent == 1){
                        Toast.makeText(getContext(), "¡No seleccionaste ninguna imagen!", Toast.LENGTH_SHORT).show();
                    } else if(tipoIntent == 2){
                        Toast.makeText(getContext(), "¡No tomaste niguna foto!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.btnSelectImage:

                tipoIntent = 1;
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                gcObtenerFoto.launch(i);

                return true;
            case R.id.btnTakePhoto:

                tipoIntent = 2;
                dispatchTakePictureIntent();

                return true;

            default:
                break;
        }

        return false;
    }

    //Método para tomar foto
    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile = null;
            try {
                photoFile = utils.createImageFile(getActivity());
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                gcObtenerFoto.launch(takePictureIntent);
            }
        }


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