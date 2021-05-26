package com.example.instock;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.instock.BD.ProductosManagerDB;
import com.example.instock.interfaces.RecyclerViewClickInterface;
import com.example.instock.models.ListaClientes;
import com.example.instock.models.ModalDialogValues;
import com.example.instock.models.Producto;
import com.example.instock.Adapter.ProductoAdaptadpr;
import com.example.instock.utils.CreateDialog;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;


public class ConsultaProductosFragment extends Fragment implements RecyclerViewClickInterface {

    //Vista del Layout
    View vista;

    RecyclerView recyclerProducto;
    ProductoAdaptadpr productoAdaptador;
    ArrayList<Producto> ProductoList;
    EditText edtNomProducto;
    TextInputLayout tilNomProducto;

    //Variable que almacena la posición del item al que se le hizo "swipe"
    private int  recyclerPositionItem;

    //Objeto de MyDialog
    CreateDialog createDialog = new CreateDialog();
    private ModalDialogValues modalDialogValues = ModalDialogValues.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_consulta_productos, container, false);
        cargarDatos();
        tilNomProducto = (TextInputLayout)vista.findViewById(R.id.tilNomProducto);
        edtNomProducto = (EditText)vista.findViewById(R.id.edtNomProducto);
        edtChangedListener();
        return vista;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        edtNomProducto.setText(null);
        cargarDatos();
        super.onViewStateRestored(savedInstanceState);
    }

    //Método para listar los productos con sus respectivas imágenes/fotos
    private void cargarDatos() {

        //Inovcamos el método para consultar los productos
        ProductosManagerDB productosManagerDB = new ProductosManagerDB(getContext());

        ProductoList = productosManagerDB.obtenerProductos();

        RecyclerView recyclerProducto = vista.findViewById(R.id.recyclerProductos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerProducto.setLayoutManager(layoutManager);

        //Enlazamos el simpleItemTouchCallback con el recyclerProducto
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerProducto);

        productoAdaptador = new ProductoAdaptadpr( ProductoList, getActivity(), this);

        recyclerProducto.setAdapter(productoAdaptador);
    }

    //Método para listar los productos según el valor escrito en edtNomProducto
    private void cargarDatosLike(String nomProducto) {

        //Inovcamos el método para consultar los productos
        ProductosManagerDB productosManagerDB = new ProductosManagerDB(getContext());

        ProductoList = productosManagerDB.obtenerProductosLike(nomProducto);

        RecyclerView recyclerProducto = vista.findViewById(R.id.recyclerProductos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerProducto.setLayoutManager(layoutManager);

        //Enlazamos el simpleItemTouchCallback con el recyclerProducto
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerProducto);

        productoAdaptador = new ProductoAdaptadpr( ProductoList, getActivity(), this);

        recyclerProducto.setAdapter(productoAdaptador);
    }

    //Método para enlazar los editText con el ChangedListener
    private void edtChangedListener(){

        edtNomProducto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 1){
                    cargarDatosLike(s.toString());
                }
                else{
                    cargarDatos();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //Objeto de tipo ItemTouchHelper que permite realizar el swipe
    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        //Método para cambiar el color del Background del Item según la dirección del "swipe"
        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                // Get RecyclerView item from the ViewHolder
                View itemView = viewHolder.itemView;

                Paint p = new Paint();
                if (dX > 0) {
                    /* Set your color for positive displacement */
                    p.setColor(getResources().getColor(R.color.azul_marino));

                    // Draw Rect with varying right side, equal to displacement dX
                    c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX,
                            (float) itemView.getBottom(), p);
                } else {
                    /* Set your color for negative displacement */
                    p.setColor(getResources().getColor(R.color.rojo));

                    // Draw Rect with varying left side, equal to the item's right side plus negative displacement dX
                    c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(),
                            (float) itemView.getRight(), (float) itemView.getBottom(), p);
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }

        //Método obligatorio para implementar el ItemTouchHelper.SimpleCallback()
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            Toast.makeText(getContext(), "on Move", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Método que realiza acciones cuando se hace el swipe
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

            //swipeDir = 4 Indica swipe hacia la izquierda
            //swipeDir = 8 Indica swipe hacia la derecha
            if(swipeDir == 4){
                //Cancelar reserva
                recyclerPositionItem = viewHolder.getAdapterPosition();
                eliminarProducto(recyclerPositionItem);
            }
            else if(swipeDir == 8){
                //Convertir venta en reserva
                recyclerPositionItem = viewHolder.getAdapterPosition();
                modificarProducto(recyclerPositionItem);
            }

        }



    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void eliminarProducto(int recyclerPositionItem){
        //Asignamos los valores para mostrar el Dialog
        modalDialogValues.modalDialogValues(getResources().getString(R.string.eliminar_producto_title),
                getResources().getString(R.string.eliminar_producto_message));

        //Invocamos el dialog() y sobreescribimos sus metodos setPositiveButton y setNegativeButton
        createDialog.dialog(getContext()).setPositiveButton(null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int idProd = Integer.parseInt(ProductoList.get(recyclerPositionItem).getIdProd());

                ProductosManagerDB productosManagerDB = new ProductosManagerDB(getContext());

                //Invocámos el método para eliminar el Producto
                long resultado = productosManagerDB.eliminarProducto(idProd);

                if(resultado > 0){
                    ProductoList.remove(recyclerPositionItem);//Removemos el item segun la posición
                    productoAdaptador.notifyDataSetChanged();//Notoficamos el cambio al Adaptador del RecyclerView
                    Toast.makeText(getContext(), "Se eliminó el producto", Toast.LENGTH_SHORT).show();
                } else {
                    productoAdaptador.notifyDataSetChanged();//Notoficamos el cambio al Adaptador del RecyclerView
                    Toast.makeText(getContext(), "No se pudo eliminar el producto", Toast.LENGTH_SHORT).show();
                }

            }
        }).setNegativeButton(null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                productoAdaptador.notifyDataSetChanged();

            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                productoAdaptador.notifyDataSetChanged();
            }
        }).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void modificarProducto(int recyclerPositionItem){
        modalDialogValues.modalDialogValues(getResources().getString(R.string.go_to_modificar_producto_title),
                getResources().getString(R.string.go_to_modificar_producto_message));

        //Invocamos el dialog() y sobreescribimos sus metodos setPositiveButton y setNegativeButton
        createDialog.dialog(getContext()).setPositiveButton(null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int idProd = Integer.parseInt(ProductoList.get(recyclerPositionItem).getIdProd());

                //Argumentos a enviar
                Bundle argumentos = new Bundle();
                argumentos.putInt("idProdParametro", idProd);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fModificarProductos = new ModificarProductosFragment();
                fModificarProductos.setArguments(argumentos);
                //Lo enviamos al Fragment de ModificarProductos
                transaction.replace(R.id.fragment_container_view, fModificarProductos);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        }).setNegativeButton(null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                productoAdaptador.notifyDataSetChanged();

            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                productoAdaptador.notifyDataSetChanged();
            }
        }).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void reservarProducto(int idProd){
        modalDialogValues.modalDialogValues(getResources().getString(R.string.go_to_reservas_title),
                getResources().getString(R.string.go_to_reservas_message));

        //Invocamos el dialog() y sobreescribimos sus metodos setPositiveButton y setNegativeButton
        createDialog.dialog(getContext()).setPositiveButton(null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Argumentos a enviar
                Bundle argumentos = new Bundle();
                argumentos.putInt("idProdParametro", idProd);

                //Lo enviamos al Fragment de ReservarProductos
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fReservarProductos = new ReservarProductosFragment();
                fReservarProductos.setArguments(argumentos);
                transaction.replace(R.id.fragment_container_view, fReservarProductos);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        }).setNegativeButton(null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Nada
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                productoAdaptador.notifyDataSetChanged();
            }
        }).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(int position) {
        int cantProd = Integer.parseInt(ProductoList.get(position).getCantidad());

        if(cantProd > 0){
            //Llamamos el método que nos lleva a la pantalla para reservar el Producto
            int idProd = Integer.parseInt(ProductoList.get(position).getIdProd());
            reservarProducto(idProd);
        } else {
            modalDialogValues.modalDialogValues("Producto Sin Existencias",
                    "No puedes reservar este producto ya que no posee existencias.\n\n" +
                            "Si quieres reservarlo debes agregar existencias en la opción de Modificar.");

            //Invocamos el dialog() y sobreescribimos sus metodos setPositiveButton y setNegativeButton
            createDialog.dialog(getContext()).setPositiveButton(null, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setNegativeButtonIcon(null)
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            productoAdaptador.notifyDataSetChanged();
                        }
                    }).show();
        }

    }
}