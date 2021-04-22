package com.example.instock;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.instock.Adapter.Producto;
import com.example.instock.Adapter.ProductoAdaptadpr;

import java.util.ArrayList;


public class ConsultaProductosFragment extends Fragment {

    RecyclerView recyclerProducto;
    ProductoAdaptadpr productoAdaptador;
    ArrayList<Producto> ProductoList;
    Button btnm;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_consulta_productos, container, false);

        //Agregado
        ProductoList = new ArrayList<>();
        //btnm = vista.findViewById(R.id.btnModifica);
        RecyclerView recyclerProducto = vista.findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerProducto.setLayoutManager(layoutManager);

        cargarDatos();
        productoAdaptador = new ProductoAdaptadpr( ProductoList, getActivity());

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

                ProductoList.add(new Producto("Producto: Camisa verde", "Categoria: Camisas","Cantidad: 25 Unidades","Precio $13.50"));
                ProductoList.add(new Producto("Producto: Short Azul", "Categoria: Short","Cantidad: 25 Unidades","Precio $8.50"));
                ProductoList.add(new Producto("Producto: Zapatos de vestir", "Categoria: Zapatos","Cantidad: 12 Unidades","Precio $25.75"));
                ProductoList.add(new Producto("Producto: Collar", "Categoria: Joyeria","Cantidad: 5 Unidades","Precio $2.50"));
                ProductoList.add(new Producto("Producto: Audifonos", "Categoria: Auriculares","Cantidad: 12 Unidades","Precio 7.00"));
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
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

            //swipeDir = 4 Indica swipe hacia la izquierda
            //swipeDir = 8 Indica swipe hacia la derecha
            if(swipeDir == 4){
                //Eliminar item
                //Remove swiped item from list and notify the RecyclerView
                int position = viewHolder.getAdapterPosition();//Obtenemos la posición del item
                ProductoList.remove(position);//Removemos el item segun la posición
                productoAdaptador.notifyDataSetChanged();//Notoficamos el cambio al Adaptador del RecyclerView

                Toast.makeText(getContext(), "¿Desea eliminar el producto?", Toast.LENGTH_SHORT).show();

            }
            else if(swipeDir == 8){
                //Modificar item
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fModificarProductos = new ModificarProductosFragment();
                //Lo enviamos al Fragment de ModificarProductos
                transaction.replace(R.id.fragment_container_view, fModificarProductos);
                transaction.addToBackStack(null);
                transaction.commit();
            }

        }

    };


}