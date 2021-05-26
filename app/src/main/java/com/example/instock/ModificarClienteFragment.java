package com.example.instock;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.instock.BD.ClientesManagerDB;
import com.example.instock.BD.ProductosManagerDB;
import com.example.instock.models.ListaClientes;
import com.example.instock.models.ModalDialogValues;
import com.example.instock.utils.CreateDialog;
import com.example.instock.utils.Utils;
import com.google.android.material.textfield.TextInputLayout;

public class ModificarClienteFragment extends Fragment {

    ImageView imgvUsuario;
    EditText edtNombres, edtApellidos, edtTel, edtCorreoElectronico;
    TextInputLayout tilNombres, tilApellidos, tilTel, tilCorreoElectronico;
    Spinner sprSexo;
    Button btnActualizar, btnCancelar;

    ArrayAdapter generosAdapter;

    Utils utils = new Utils();

    ListaClientes listaClientes;

    //Objeto de MyDialog
    CreateDialog createDialog = new CreateDialog();
    private ModalDialogValues modalDialogValues = ModalDialogValues.getInstance();

    int posGenero;//Variable para almacenar la posición del sprSexo

    //Setter de posGenero
    private void setPosGenero(int position) {
        this.posGenero = position;
    }

    //ID del cliente recibido por parámetro
    int idClienteParametro = 0;

    //Constructor del Fragment
    ModificarClienteFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //Obtenemos los datos enviados desde ConsultarProductosFragment
        Bundle datosRecuperados = getArguments();
        if(datosRecuperados != null){
            idClienteParametro = datosRecuperados.getInt("idClienteParametro");
        }

        ((MainMenu)getActivity()).setFragmentActivo(true);//Indicamos que hay un Framgent Activo
        ((MainMenu)getActivity()).displayBackArrowOrHamburger(getContext(), 1);//Invocamos el método
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modificar_cliente, container, false);
        enlazarVistas(view);
        actualizar();
        cancelar();
        asignarValoresAVistas();
        return view;
    }

    private void enlazarVistas(View v){
        imgvUsuario = (ImageView)v.findViewById(R.id.imgvUsuario);
        edtNombres = (EditText)v.findViewById(R.id.edtNombres);
        edtApellidos = (EditText)v.findViewById(R.id.edtApellidos);
        edtTel = (EditText)v.findViewById(R.id.edtTel);
        edtCorreoElectronico = (EditText)v.findViewById(R.id.edtCorreoElectronico);
        tilNombres = (TextInputLayout) v.findViewById(R.id.tilNombres);
        tilApellidos = (TextInputLayout) v.findViewById(R.id.tilApellidos);
        tilTel = (TextInputLayout) v.findViewById(R.id.tilTel);
        tilCorreoElectronico = (TextInputLayout) v.findViewById(R.id.tilCorreoElectronico);
        sprSexo = (Spinner)v.findViewById(R.id.sprSexo);
        btnActualizar = (Button)v.findViewById(R.id.btnActualizar);
        btnCancelar = (Button)v.findViewById(R.id.btnCancelar);

        String[] generos = getResources().getStringArray(R.array.genero);
        generosAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, generos);
        sprSexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {

                    setPosGenero(position);

                    Drawable imgNueva;
                    if(position == 1){
                        //Sexo MASCULINO
                        imgNueva = (Drawable)getResources().getDrawable(R.drawable.ic_outline_account_circle_24);
                        imgvUsuario.setImageDrawable(imgNueva);
                    } else if(position == 2){
                        //Sexo FEMENINO
                        imgNueva = (Drawable)getResources().getDrawable(R.drawable.ic_outline_account_circle_24_pink);
                        imgvUsuario.setImageDrawable(imgNueva);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sprSexo.setAdapter(generosAdapter);
    }

    //Asignamos los valores a las vistas según el "idProd"
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void asignarValoresAVistas(){

        //Inovcamos el método para consultar el cliente
        ClientesManagerDB clientesManagerDB = new ClientesManagerDB(getContext());

        listaClientes = clientesManagerDB.obtenerCliente(String.valueOf(idClienteParametro));

        edtNombres.setText(listaClientes.getNombre());
        edtApellidos.setText(listaClientes.getApellido());
        edtTel.setText(listaClientes.getTelefono());
        edtCorreoElectronico.setText(listaClientes.getCorreo());

        for(int i = 0; i < generosAdapter.getCount(); i++){
            if(listaClientes.getSexo().trim().equals(generosAdapter.getItem(i).toString().trim())){
                sprSexo.setSelection(i);
                break;
            }
        }
    }

    //Método que activa la escucha del onChange de los EditText
    private void edtChangeListenerAll(){
        //Método para quitar alerta en onChanged
        edtChangedListener(edtNombres, tilNombres);
        edtChangedListener(edtApellidos, tilApellidos);
        edtChangedListener(edtTel, tilTel);
        edtChangedListener(edtCorreoElectronico, tilCorreoElectronico);
    }

    //Método para enlazar los editText con el ChangedListener
    private void edtChangedListener(EditText editText, TextInputLayout til){
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
                    til.setError(getText(R.string.campo_requerido));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void actualizar(){
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                if(edtNombres.getText().toString().equals("")
                        || edtApellidos.getText().toString().equals("")
                        || edtTel.getText().toString().equals("")
                        || edtCorreoElectronico.getText().toString().equals("")
                        || posGenero == 0){

                    if(edtNombres.getText().toString().equals("")){
                        tilNombres.setError(getResources().getString(R.string.campo_requerido));
                    }
                    if(edtApellidos.getText().toString().equals("")){
                        tilApellidos.setError(getResources().getString(R.string.campo_requerido));
                    }
                    if(edtTel.getText().toString().equals("")){
                        tilTel.setError(getResources().getString(R.string.campo_requerido));
                    }
                    if(edtCorreoElectronico.getText().toString().equals("")){
                        tilCorreoElectronico.setError(getResources().getString(R.string.campo_requerido));
                    }
                    if(posGenero == 0){
                        Toast.makeText(getContext(), "Debes seleccionar un género",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {

                    //Asignamos los valores para mostrar el Dialog
                    modalDialogValues.modalDialogValues(getResources().getString(R.string.modificar_cliente_title),
                            getResources().getString(R.string.modificar_cliente_message));

                    //Invocamos el dialog() y sobreescribimos sus metodos setPositiveButton y setNegativeButton
                    createDialog.dialog(getContext()).setPositiveButton(null, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //Obtenemos los datos a almacenar
                            int idCliente = Integer.parseInt(listaClientes.getIdCliente());
                            String nombresCliente = edtNombres.getText().toString();
                            String apellidosCliente = edtApellidos.getText().toString();
                            String sexoCliente = sprSexo.getSelectedItem().toString();
                            String telefonoCliente = edtTel.getText().toString();
                            String correoCliente = edtCorreoElectronico.getText().toString();

                            ClientesManagerDB clientesManagerDB = new ClientesManagerDB(getContext());
                            //Inovcamos el método para modificar el registro a la BD
                            int resultado = clientesManagerDB.modificarCliente(idCliente,
                                    nombresCliente, apellidosCliente, sexoCliente, telefonoCliente, correoCliente);

                            if(resultado != -1){
                                Toast.makeText(getContext(), "¡Cliente modificado satisfactoriamente!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "¡No se pudo modificar el cliente!", Toast.LENGTH_SHORT).show();
                            }

                            limpiarCampos();
                            //Deshabilitamos el BackButton del ActionBar y mostramos la hamburguesa del menú
                            ((MainMenu)getActivity()).displayHamburguer();
                            getParentFragmentManager().popBackStack();//Cerramos el fragment
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
                        asignarValoresAVistas();

                    }
                }).setNegativeButton(null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });
    }

    public void limpiarCampos(){
        // Limpiamos el EditText
        edtNombres.setText("");
        edtApellidos.setText("");
        edtTel.setText("");
        edtCorreoElectronico.setText("");
        sprSexo.setSelection(0);

        tilNombres.setError(null);
        tilApellidos.setError(null);
        tilTel.setError(null);
        tilCorreoElectronico.setError(null);

        edtNombres.requestFocus();
    }
}