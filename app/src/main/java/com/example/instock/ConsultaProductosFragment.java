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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.instock.interfaces.RecyclerViewClickInterface;
import com.example.instock.models.ModalDialogValues;
import com.example.instock.models.Producto;
import com.example.instock.Adapter.ProductoAdaptadpr;
import com.example.instock.utils.CreateDialog;

import java.util.ArrayList;


public class ConsultaProductosFragment extends Fragment implements RecyclerViewClickInterface {

    RecyclerView recyclerProducto;
    ProductoAdaptadpr productoAdaptador;
    ArrayList<Producto> ProductoList;
    Producto producto;

    //Variable que almacena la posición del item al que se le hizo "swipe"
    private int  recyclerPositionItem;

    //Objeto de MyDialog
    CreateDialog createDialog = new CreateDialog();
    private ModalDialogValues modalDialogValues = ModalDialogValues.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_consulta_productos, container, false);

        //Agregado
        ProductoList = new ArrayList<>();

        RecyclerView recyclerProducto = vista.findViewById(R.id.recyclerProductos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerProducto.setLayoutManager(layoutManager);

        cargarDatos();
        productoAdaptador = new ProductoAdaptadpr( ProductoList, getActivity(), this);

        recyclerProducto.setAdapter(productoAdaptador);

        cargarDatos();

        //Enlazamos el simpleItemTouchCallback con el recyclerProducto
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerProducto);

        return vista;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    private void cargarDatos() {

                ProductoList.add(new Producto("Camisa verde", "Camisas","25 Unidades","$13.50"));
                ProductoList.add(new Producto("Short Azul", "Short","25 Unidades","$8.50"));
                ProductoList.add(new Producto("Zapatos de vestir", "Zapatos","12 Unidades","$25.75"));
                ProductoList.add(new Producto("Producto: Collar", "Joyeria","5 Unidades","$2.50"));
                ProductoList.add(new Producto("Audifonos", "Auriculares","12 Unidades","7.00"));
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
                eliminarProducto();
            }
            else if(swipeDir == 8){
                //Convertir venta en reserva
                recyclerPositionItem = viewHolder.getAdapterPosition();
                modificarProducto();
            }

        }



    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void eliminarProducto(){
        //Asignamos los valores para mostrar el Dialog
        modalDialogValues.modalDialogValues(getResources().getString(R.string.eliminar_producto_title),
                getResources().getString(R.string.eliminar_producto_message));

        //Invocamos el dialog() y sobreescribimos sus metodos setPositiveButton y setNegativeButton
        createDialog.dialog(getContext()).setPositiveButton(null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ProductoList.remove(recyclerPositionItem);//Removemos el item segun la posición
                productoAdaptador.notifyDataSetChanged();//Notoficamos el cambio al Adaptador del RecyclerView
                Toast.makeText(getContext(), "Producto eliminado", Toast.LENGTH_SHORT).show();

            }
        }).setNegativeButton(null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                productoAdaptador.notifyDataSetChanged();

            }
        }).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void modificarProducto(){
        modalDialogValues.modalDialogValues(getResources().getString(R.string.go_to_modificar_producto_title),
                getResources().getString(R.string.go_to_modificar_producto_message));

        //Invocamos el dialog() y sobreescribimos sus metodos setPositiveButton y setNegativeButton
        createDialog.dialog(getContext()).setPositiveButton(null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fModificarProductos = new ModificarProductosFragment();
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
        }).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void reservarProducto(){
        modalDialogValues.modalDialogValues(getResources().getString(R.string.go_to_reservas_title),
                getResources().getString(R.string.go_to_reservas_message));

        //Invocamos el dialog() y sobreescribimos sus metodos setPositiveButton y setNegativeButton
        createDialog.dialog(getContext()).setPositiveButton(null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Agregar pantalla de Reservar Producto
                //Redirigir a pantalla de Reservar Producto

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fReservarProductos = new ReservarProductosFragment();
                //Lo enviamos al Fragment de ModificarProductos
                transaction.replace(R.id.fragment_container_view, fReservarProductos);
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(int position) {
        Toast.makeText(getContext(), ProductoList.get(position).getNomProducto(), Toast.LENGTH_SHORT).show();
        reservarProducto();
    }
}