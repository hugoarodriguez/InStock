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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.instock.BD.CategoriasManagerDB;
import com.example.instock.BD.ProductosManagerDB;
import com.example.instock.models.ModalDialogValues;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.instock.utils.*;


public class AgregarProductosFragment extends Fragment {

    public AgregarProductosFragment(){}

    ImageView imgProducto;
    ImageButton btnSelectImage, btnTakePhoto;
    Button btnAgregar, btnCancelar;
    TextInputLayout tilNombrePro, tilCantPro, tilPrecioPro, tilDetallesPro;
    EditText edtNombrePro, edtCantPro, edtPrecioPro, edtDetallesPro;
    Spinner sprCategoria;

    ArrayAdapter<String> categoriasAdaptador;

    String mensajeAlerta = "Dato requerido";

    static final int REQUEST_TAKE_PHOTO = 1;//Variable para tomar foto

    File photoFile = null;
    Uri photoURI = null;
    Utils utils = new Utils();
    int tipoIntent = 0; //1 - Seleccionar Imagen, 2 - Tomar Foto

    String urlFoto = null;

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

        cargarCategorias();//Método para llenar Spinner
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
        sprCategoria = (Spinner)v.findViewById(R.id.sprCategoria);
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

                boolean spinnerValidation = utils.validateSpinner(getContext(),
                        categoriasAdaptador, sprCategoria, R.string.spr_categoria_first_value);

                if(edtNombrePro.getText().toString().equals("")
                        || edtCantPro.getText().toString().equals("") || Integer.parseInt(edtCantPro.getText().toString()) <= 0
                        || edtPrecioPro.getText().toString().equals("") || Double.parseDouble(edtPrecioPro.getText().toString()) < 1.00
                        || edtDetallesPro.getText().toString().equals("")
                        || spinnerValidation){

                    if(edtNombrePro.getText().toString().equals("")){
                        tilNombrePro.setError(mensajeAlerta);
                    }
                    if(edtCantPro.getText().toString().equals("")
                            || Integer.parseInt(edtCantPro.getText().toString()) <= 0){
                        tilCantPro.setError(mensajeAlerta);
                    }
                    if(edtPrecioPro.getText().toString().equals("")
                            || Double.parseDouble(edtPrecioPro.getText().toString()) < 1.00){
                        tilPrecioPro.setError(mensajeAlerta);
                    }
                    if(edtDetallesPro.getText().toString().equals("")){
                        tilDetallesPro.setError(mensajeAlerta);
                    }

                    if(spinnerValidation){
                        Toast.makeText(getContext(), "¡Debes seleccionar una categoría!",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {

                    //Asignamos los valores para mostrar el Dialog
                    modalDialogValues.modalDialogValues("Agregar Producto",
                            "¿Estás seguro que deseas agregar este producto?");

                    //Invocamos el dialog() y sobreescribimos sus metodos setPositiveButton y setNegativeButton
                    createDialog.dialog(getContext()).setPositiveButton(null, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //Obtenemos los datos a almacenar
                            String nomProd = edtNombrePro.getText().toString();
                            int cantProd = Integer.parseInt(edtCantPro.getText().toString());
                            double precioProd = Double.parseDouble(edtPrecioPro.getText().toString());
                            String detalles = edtDetallesPro.getText().toString();

                            CategoriasManagerDB categoriasManagerDB = new CategoriasManagerDB();
                            int idCatProd = categoriasManagerDB.getIDCategoriaByName(getContext(),
                                    sprCategoria.getSelectedItem().toString());

                            ProductosManagerDB productosManagerDB = new ProductosManagerDB();
                            //Inovcamos el método para agregar el registro a la BD
                            long resultado = productosManagerDB.agregarProducto(getContext(), nomProd, cantProd,
                                    precioProd, detalles, urlFoto, idCatProd);

                            if(resultado != -1){
                                Toast.makeText(getContext(), "¡Producto agregado satisfactoriamente!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "¡No se pudo agregar el producto!", Toast.LENGTH_SHORT).show();
                            }

                            limpiarCampos();

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
                modalDialogValues.modalDialogValues(getResources().getString(R.string.cancelar_title),
                        getResources().getString(R.string.cancelar_message));

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

                    if(tipoIntent == 1){
                        //Cuando se selecciona una foto de la galería

                        Uri uri = result.getData().getData();
                        InputStream is = null;
                        try {
                            //Obtenemos el InputStream según la "uri" proporcionada
                            is = getActivity().getContentResolver().openInputStream(uri);

                            //Creamos el bitmap de nuestra imagen
                            Bitmap bitmap = BitmapFactory.decodeStream(is);
                            int rotation = utils.getRotationFromGallery(getContext(), uri);
                            int rotationInDegrees = utils.exifToDegrees(rotation);

                            //Rotamos la imagen para que quede de forma vertical
                            Bitmap imagenFinal = utils.rotateAndBitmapConvert(rotation, rotationInDegrees, bitmap);
                            imgProducto.setImageBitmap(imagenFinal);

                            photoFile = null;
                            try {
                                //Guardar imagen en directorio de la App
                                photoFile = utils.createImageFile(getActivity());

                                // Si se creó el archivo lo almacenamos en el directorio de la App
                                if (photoFile != null) {
                                    FileOutputStream out = new FileOutputStream(photoFile);
                                    imagenFinal.compress(Bitmap.CompressFormat.PNG, 100, out);
                                    out.flush();
                                    out.close();

                                    //Asignamos la imagen seleccionada al ImageView "imgProducto"
                                    Glide.with(getContext()).load(photoFile.getAbsoluteFile().getAbsolutePath()).into(imgProducto);

                                    //Variable para almacenar la ruta de la imagen
                                    urlFoto = photoFile.getAbsoluteFile().getAbsolutePath();
                                }
                            } catch (IOException ex) {
                                // Error occurred while creating the File
                                Toast.makeText(getContext(), "¡Algo salió mal! Intenta de nuevo.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "¡Algo salió mal! Intenta de nuevo.", Toast.LENGTH_SHORT).show();
                        }

                    } else if(tipoIntent == 2){
                        //Cuando se toma foto
                        try {
                            Glide.with(getContext()).load(photoFile.getAbsoluteFile().getAbsolutePath()).into(imgProducto);

                            //Variable para almacenar la ruta de la imagen
                            urlFoto = photoFile.getAbsoluteFile().getAbsolutePath();

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "¡Algo salió mal! Intenta de nuevo.", Toast.LENGTH_SHORT).show();
                        }
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

    //Método para llenar "sprCategoria"
    private void cargarCategorias(){
        CategoriasManagerDB categoriasManagerDB = new CategoriasManagerDB();
        List<String> categorias = new ArrayList<>();

        categorias = categoriasManagerDB.getCategorias(getContext());

        categoriasAdaptador = new ArrayAdapter<>(getContext()
                , android.R.layout.simple_spinner_dropdown_item
                , categorias);

        sprCategoria.setAdapter(categoriasAdaptador);
    }

    private void limpiarCampos(){
        edtNombrePro.setText("");
        edtCantPro.setText("");
        edtPrecioPro.setText("");
        edtDetallesPro.setText("");
        sprCategoria.setSelection(0);

        //Ruta de acceso a la imagen "sin_imagen.jpg"
        Uri uri = Uri.parse("android.resource://com.example.instock/drawable/sin_imagen");
        imgProducto.setImageURI(uri);
        urlFoto = null;

        tilNombrePro.setError(null);
        tilCantPro.setError(null);
        tilPrecioPro.setError(null);
        tilDetallesPro.setError(null);
    }
}