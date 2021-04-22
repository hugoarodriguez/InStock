package com.example.instock;

import android.graphics.Canvas;
import android.graphics.Paint;
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
import android.widget.Toast;

import com.example.instock.Adapter.ClientesAdaptador;
import com.example.instock.Adapter.Producto;
import com.example.instock.Adapter.ProductoAdaptadpr;

import java.util.ArrayList;
import java.util.List;


public class VerClientesFragment extends Fragment {

    ClientesAdaptador clienteAdaptador;
    List<ListaClientes> ClientesList = new ArrayList<>();
    RecyclerView recyclerCliente;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ver_clientes, container, false);
        ;
        //Enlazamos el simpleItemTouchCallback con el recyclerProducto

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cargarDatos();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerCliente);
    }

    private void cargarDatos() {
        ClientesList.add(new ListaClientes("Nombre: AMILTON ABRAHAM", "Teléfono: 7859-8598", "Correo: algo@gmail.com", "Sexo: Masculino"));
        ClientesList.add(new ListaClientes("Nombre: HUGO ALEXANDER", "Teléfono: 7859-8598", "Correo: algo@gmail.com", "Sexo: Masculino"));
        ClientesList.add(new ListaClientes("Nombre: SAMUEL ENRIQUE", "Teléfono: 7859-8598", "Correo: algo@gmail.com", "Sexo: Masculino"));
        ClientesList.add(new ListaClientes("Nombre: BLADIMIR STANLEY", "Teléfono: 7859-8598", "Correo: algo@gmail.com", "Sexo: Masculino"));
        ClientesList.add(new ListaClientes("Nombre: ISAI", "Teléfono: 7859-8598", "Correo: algo@gmail.com", "Sexo: Masculino"));

        recyclerCliente = getView().findViewById(R.id.recyclerClientes);
        recyclerCliente.setLayoutManager(new LinearLayoutManager(getActivity()));
        clienteAdaptador = new ClientesAdaptador(ClientesList, getActivity());
        recyclerCliente.setAdapter(clienteAdaptador);
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
            if (swipeDir == 4) {
                //Eliminar item
                //Remove swiped item from list and notify the RecyclerView
                int position = viewHolder.getAdapterPosition();//Obtenemos la posición del item
                ClientesList.remove(position);//Removemos el item segun la posición
                clienteAdaptador.notifyDataSetChanged();//Notoficamos el cambio al Adaptador del RecyclerView

                Toast.makeText(getContext(), "¿Desea eliminar el producto?", Toast.LENGTH_SHORT).show();

            } else if (swipeDir == 8) {
                //Modificar item
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fmodificar = new ModificarClienteFragment();

                transaction.replace(R.id.fragment_container_view, fmodificar);
                transaction.addToBackStack(null);
                transaction.commit();
            }

        }
    };
}