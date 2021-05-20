package com.example.instock;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.instock.BD.ClientesManagerDB;
import com.example.instock.BD.ProductosManagerDB;
import com.example.instock.BD.ReservasManagerDB;
import com.example.instock.models.ListaClientes;
import com.example.instock.models.ModalDialogValues;
import com.example.instock.models.Producto;
import com.example.instock.utils.CreateDialog;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class ReservarProductosFragment extends Fragment {

    public ReservarProductosFragment() {
        // Required empty public constructor
    }

    Producto producto;

    //ID del producto recibido por parámetro
    int idProdParametro = 0;

    //Objeto de MyDialog
    CreateDialog createDialog = new CreateDialog();
    private ModalDialogValues modalDialogValues = ModalDialogValues.getInstance();

    ImageView imgProducto;
    TextView tvProductoVal, tvPrecioVal;
    AutoCompleteTextView actvCliente;
    EditText edtCantidad;
    TextInputLayout tilCantidad;
    ImageButton btnAddCantidad, btnSubCantidad;
    Button  btnReservar, btnCancelar;

    int idCliente = 0;

    int cantidad = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //Obtenemos los datos enviados desde ConsultarProductosFragment
        Bundle datosRecuperados = getArguments();
        if(datosRecuperados != null){
            idProdParametro = datosRecuperados.getInt("idProdParametro");
        }

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

        //Invocamos los métodos para sumar/restar valores al "edtCantidad"
        addCantidad();
        subCantidad();

        //Invocamos los métodos para agregar/cancelar la Reserva
        reservar();
        cancelar();

        //Asignamos los valores iniciales a los Campos
        asignarValoresAVistas(String.valueOf(idProdParametro));
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
        imgProducto = (ImageView)v.findViewById(R.id.imgProducto);
        tvProductoVal = (TextView)v.findViewById(R.id.tvProductoVal);
        tvPrecioVal = (TextView)v.findViewById(R.id.tvPrecioVal);
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
        ArrayList<String> nombresClientes = clientesManagerDB.obtenerCorreosClientes();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, nombresClientes);
        actvCliente.setAdapter(adapter);
        actvCliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Obtenemos el ID del Cliente
                System.out.println("Adapter position: " + adapter.getItem(position));
                idCliente = clientesManagerDB.getIdClienteByCorreo(adapter.getItem(position).trim());
                System.out.println("Id Cliente: " + idCliente);
            }
        });
    }

    //Asignamos los valores a las vistas según el "idProd"
    private void asignarValoresAVistas(String idProd){

        //Inovcamos el método para consultar los productos
        ProductosManagerDB productosManagerDB = new ProductosManagerDB();

        producto = productosManagerDB.obtenerProducto(getContext(), String.valueOf(idProdParametro));

        Glide.with(getContext()).load(producto.getFotoProd()).into(imgProducto);
        tvProductoVal.setText(producto.getNomProducto());
        tvPrecioVal.setText("$"+producto.getPrecio());
        edtCantidad.setText("1");//Colocamos 1 en el valor inicial de la cantidad de producto a reservar
        cantidad = Integer.parseInt(producto.getCantidad());
    }

    public void addCantidad(){
        btnAddCantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Evaluar si "cantidad" es igual o mayor que el valor máximo de cantidad extraído de la BD
                if(cantidad < Integer.parseInt(producto.getCantidad())){
                    cantidad++;
                    edtCantidad.setText(Integer.toString(cantidad));
                }
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
                            ReservasManagerDB reservasManagerDB = new ReservasManagerDB(getContext());

                            long resultado = reservasManagerDB.agregarReserva(idProdParametro, idCliente,
                                    Integer.parseInt(producto.getCantidad()), cantidad, Double.parseDouble(producto.getPrecio()));

                            if(resultado != -1){
                                Toast.makeText(getContext(), "¡Producto reservado satisfactoriamente!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "¡No se pudo reservar el producto!", Toast.LENGTH_SHORT).show();
                            }

                            limpiarCampos();
                            //Deshabilitamos el BackButton del ActionBar y mostramos la hamburguesa del menú
                            ((MainMenu)getActivity()).displayHamburguer();
                            getParentFragmentManager().popBackStack();//Cerramos el fragment

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