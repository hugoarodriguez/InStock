package com.example.instock;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.instock.BD.ClientesManagerDB;
import com.example.instock.models.ModalDialogValues;
import com.example.instock.utils.CreateDialog;
import com.google.android.material.textfield.TextInputLayout;

public class AgregarClienteFragment extends Fragment {
    // Declaración de variables
    EditText edtNombres, edtApellidos, edtTelefono, edtCorreo;
    TextInputLayout tilNombres, tilApellidos, tilTelefono, tilCorreo;
    Spinner sprSexo;
    Button btnAgregar, btnCancelar;

    //Objeto de MyDialog
    CreateDialog createDialog = new CreateDialog();
    private ModalDialogValues modalDialogValues = ModalDialogValues.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agregar_cliente, container, false);
    }

    int posGenero;

    private void setPosGenero(int position) {
        this.posGenero = position;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enlazarVistas();
        edtChangeListenerAll();
        agregarCliente();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        limpiarCampos();
        super.onViewStateRestored(savedInstanceState);
    }

    private void enlazarVistas(){
        edtNombres = (EditText) getView().findViewById(R.id.edtNombre);
        edtApellidos = (EditText) getView().findViewById(R.id.edtApellido);
        edtTelefono = (EditText) getView().findViewById(R.id.edtTelefono);
        edtCorreo = (EditText) getView().findViewById(R.id.edtCorreo);
        btnAgregar = (Button) getView().findViewById(R.id.btnAgregarC);
        btnCancelar = (Button) getView().findViewById(R.id.btnCancelarC);
        sprSexo = (Spinner) getView().findViewById(R.id.spGeneroC);
        tilNombres = (TextInputLayout)getView().findViewById(R.id.tilNombre);
        tilApellidos = (TextInputLayout)getView().findViewById(R.id.tilApellido);
        tilTelefono = (TextInputLayout)getView().findViewById(R.id.tilTelefono);
        tilCorreo = (TextInputLayout)getView().findViewById(R.id.tilCorreo);

        String[] generos = getResources().getStringArray(R.array.genero);
        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, generos);
        sprSexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {

                    setPosGenero(position);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sprSexo.setAdapter(adapter);
    }

    //Método que activa la escucha del onChange de los EditText
    private void edtChangeListenerAll(){
        //Método para quitar alerta en onChanged
        edtChangedListener(edtNombres, tilNombres);
        edtChangedListener(edtApellidos, tilApellidos);
        edtChangedListener(edtTelefono, tilTelefono);
        edtChangedListener(edtCorreo, tilCorreo);
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

    private void agregarCliente(){
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                String nomb = edtNombres.getText().toString();
                String ape = edtApellidos.getText().toString();
                String tel = edtTelefono.getText().toString();
                String corr = edtCorreo.getText().toString();

                if (nomb.trim().equals("")
                        || ape.trim().equals("")
                        || tel.trim().equals("")
                        || corr.trim().equals("")
                        || posGenero == 0) {

                    if(nomb.trim().equals("")){
                        tilNombres.setError(getText(R.string.campo_requerido));
                    }
                    if(ape.trim().equals("")){
                        tilApellidos.setError(getText(R.string.campo_requerido));
                    }
                    if(tel.trim().equals("")){
                        tilTelefono.setError(getText(R.string.campo_requerido));
                    }
                    if(corr.trim().equals("")){
                        tilCorreo.setError(getText(R.string.campo_requerido));
                    }
                    if(posGenero == 0){
                        Toast.makeText(getContext(), "Debes seleccionar un género",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {

                    //Asignamos los valores para mostrar el Dialog
                    modalDialogValues.modalDialogValues("Agregar Cliente",
                            "¿Estás seguro que deseas agregar este cliente?");

                    //Invocamos el dialog() y sobreescribimos sus metodos setPositiveButton y setNegativeButton
                    createDialog.dialog(getContext()).setPositiveButton(null, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            ClientesManagerDB clientesManagerDB = new ClientesManagerDB(getContext());
                            long resultado = clientesManagerDB.agregarCliente(nomb, ape, sprSexo.getSelectedItem().toString(), tel, corr);

                            if(resultado != -1){
                                Toast.makeText(getContext(), "Cliente agregado satisfactoriamente", Toast.LENGTH_SHORT).show();
                                limpiarCampos();
                            } else {
                                Toast.makeText(getContext(), "No se pudo agregar el cliente", Toast.LENGTH_SHORT).show();
                            }

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

    private void limpiarCampos(){
        // Limpiamos el EditText
        edtNombres.setText("");
        edtApellidos.setText("");
        edtTelefono.setText("");
        edtCorreo.setText("");
        sprSexo.setSelection(0);

        tilNombres.setError(null);
        tilApellidos.setError(null);
        tilTelefono.setError(null);
        tilCorreo.setError(null);

        edtNombres.requestFocus();
    }
}