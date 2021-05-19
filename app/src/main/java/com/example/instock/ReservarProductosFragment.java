package com.example.instock;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.instock.BD.ClientesManagerDB;
import com.example.instock.models.ModalDialogValues;
import com.example.instock.utils.CreateDialog;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class ReservarProductosFragment extends Fragment {

    public ReservarProductosFragment() {
        // Required empty public constructor
    }

    boolean fragmentCall = false;

    //Objeto de MyDialog
    CreateDialog createDialog = new CreateDialog();
    private ModalDialogValues modalDialogValues = ModalDialogValues.getInstance();

    AutoCompleteTextView actvCliente;
    EditText edtCantidad;
    TextInputLayout tilCantidad;
    ImageButton btnAddCantidad, btnSubCantidad;
    Button  btnReservar, btnCancelar;

    int cantidad = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        ((MainMenu)getActivity()).setFragmentActivo(true);//Indicamos que hay un Framgent Activo
        ((MainMenu)getActivity()).displayBackArrowOrHamburger(getContext(), 2);//Invocamos el método
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_reservar_productos, container, false);
        enlazarVistas(vista);
        actvClienteAdapter();

        //TODO: Colocar en "cantidad" el valor extraído de la BD
        edtCantidad.setText(Integer.toString(cantidad));

        //Invocamos los métodos para sumar/restar valores al "edtCantidad"
        addCantidad();
        subCantidad();

        //Invocamos los métodos para agregar/cancelar la Reserva
        reservar();
        cancelar();

        return vista;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        limpiarCampos();
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void enlazarVistas(View v){
        actvCliente = (AutoCompleteTextView)v.findViewById(R.id.actvCliente);
        edtCantidad = (EditText)v.findViewById(R.id.edtCantidad);
        tilCantidad = (TextInputLayout)v.findViewById(R.id.tilCantidad);
        btnAddCantidad = (ImageButton)v.findViewById(R.id.btnAddCantidad);
        btnSubCantidad = (ImageButton)v.findViewById(R.id.btnSubCantidad);
        btnReservar = (Button)v.findViewById(R.id.btnReservar);
        btnCancelar = (Button)v.findViewById(R.id.btnCancelar);
    }

    public void actvClienteAdapter(){
        ClientesManagerDB clientesManagerDB = new ClientesManagerDB(getContext());
        ArrayList<String> clientes = clientesManagerDB.obtenerNombresClientes();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, clientes);
        actvCliente.setAdapter(adapter);
    }

    public void addCantidad(){
        btnAddCantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Evaluar si "cantidad" es igual o mayor que el valor máximo de cantidad extraído de la BD
                cantidad++;
                edtCantidad.setText(Integer.toString(cantidad));
            }
        });
    }

    public void subCantidad(){
        btnSubCantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cantidad > 1){
                    cantidad--;
                    edtCantidad.setText(Integer.toString(cantidad));
                }
            }
        });
    }

    public void reservar(){

        btnReservar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if(actvCliente.getText().toString().isEmpty()){
                    actvCliente.setError("Debes seleccionar un Cliente");
                } else {
                    modalDialogValues.modalDialogValues(getResources().getString(R.string.reservar_title),
                            getResources().getString(R.string.reservar_message));

                    //Invocamos el dialog() y sobreescribimos sus metodos setPositiveButton y setNegativeButton
                    createDialog.dialog(getContext()).setPositiveButton(null, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //TODO: Agregar función para registrar datos en la BD


                            limpiarCampos();//Limpiamos los campos

                            //Redirigímos a la pantalla de ConsultarProductos
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            Fragment fConsultarProductos = new ConsultaProductosFragment();
                            //Lo enviamos al Fragment de ModificarProductos
                            transaction.replace(R.id.fragment_container_view, fConsultarProductos);
                            transaction.addToBackStack(null);
                            transaction.commit();

                        }
                    }).setNegativeButton(null, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Nada
                        }
                    }).show();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void cancelar(){

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modalDialogValues.modalDialogValues(getResources().getString(R.string.cancelar_title),
                        getResources().getString(R.string.cancelar_message));

                //Invocamos el dialog() y sobreescribimos sus metodos setPositiveButton y setNegativeButton
                createDialog.dialog(getContext()).setPositiveButton(null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //TODO: Limpiar "edtCliente" y resetear "edtCantidad"
                        System.out.println("Limpiar");
                        limpiarCampos();

                    }
                }).setNegativeButton(null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Nada
                    }
                }).show();
            }
        });
    }

    private void limpiarCampos(){
        cantidad = 1;//TODO: Obtener el valor de "cantidad" de la BD
        edtCantidad.setText(Integer.toString(cantidad));
        actvCliente.setText(null);
        actvCliente.setError(null);
    }
}