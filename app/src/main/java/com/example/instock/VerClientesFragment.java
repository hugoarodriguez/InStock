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

import com.example.instock.Adapter.ClientesAdaptador;
import com.example.instock.BD.ClientesManagerDB;
import com.example.instock.models.ListaClientes;
import com.example.instock.models.ModalDialogValues;
import com.example.instock.utils.CreateDialog;

import java.util.ArrayList;
import java.util.List;


public class VerClientesFragment extends Fragment {

    ClientesAdaptador clienteAdaptador;
    List<ListaClientes> ClientesList = new ArrayList<>();
    RecyclerView recyclerCliente;

    //Variable que almacena la posición del item al que se le hizo "swipe"
    private int  recyclerPositionItem;

    //Objeto de MyDialog
    CreateDialog createDialog = new CreateDialog();
    private ModalDialogValues modalDialogValues = ModalDialogValues.getInstance();

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
    }

    private void cargarDatos() {

        ClientesManagerDB clientesManagerDB = new ClientesManagerDB(getContext());

        ClientesList = clientesManagerDB.obtenerClientes();

        recyclerCliente = getView().findViewById(R.id.recyclerClientes);
        recyclerCliente.setLayoutManager(new LinearLayoutManager(getActivity()));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerCliente);

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
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

            //swipeDir = 4 Indica swipe hacia la izquierda
            //swipeDir = 8 Indica swipe hacia la derecha
            if(swipeDir == 4){
                //Cancelar reserva
                recyclerPositionItem = viewHolder.getAdapterPosition();
                eliminarCliente(recyclerPositionItem);
            }
            else if(swipeDir == 8){
                //Convertir venta en reserva
                recyclerPositionItem = viewHolder.getAdapterPosition();
                modificarCliente(recyclerPositionItem);
            }

        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void eliminarCliente(int recyclerPositionItem){
        //Asignamos los valores para mostrar el Dialog
        modalDialogValues.modalDialogValues("Eliminar Cliente",
                "¿Estás seguro que deseas eliminar este cliente?");

        //Invocamos el dialog() y sobreescribimos sus metodos setPositiveButton y setNegativeButton
        createDialog.dialog(getContext()).setPositiveButton(null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int idCliente = Integer.parseInt(ClientesList.get(recyclerPositionItem).getIdCliente());

                ClientesManagerDB clientesManagerDB = new ClientesManagerDB(getContext());

                int resultado = clientesManagerDB.eliminarCliente(idCliente);

                if(resultado > 0){
                    ClientesList.remove(recyclerPositionItem);//Removemos el item segun la posición
                    clienteAdaptador.notifyDataSetChanged();//Notoficamos el cambio al Adaptador del RecyclerView
                    Toast.makeText(getContext(), "Cliente eliminado satisfactoriamente", Toast.LENGTH_SHORT).show();
                } else {
                    clienteAdaptador.notifyDataSetChanged();//Notoficamos el cambio al Adaptador del RecyclerView
                    Toast.makeText(getContext(), "No se puedo eliminar el cliente", Toast.LENGTH_SHORT).show();
                }

            }
        }).setNegativeButton(null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                clienteAdaptador.notifyDataSetChanged();

            }
        }).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void modificarCliente(int recyclerPositionItem){
        modalDialogValues.modalDialogValues("Modificar Cliente",
                "¿Quieres modificar este cliente?");

        //Invocamos el dialog() y sobreescribimos sus metodos setPositiveButton y setNegativeButton
        createDialog.dialog(getContext()).setPositiveButton(null, new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {

                int idCliente = Integer.parseInt(ClientesList.get(recyclerPositionItem).getIdCliente());

                Bundle argumentos = new Bundle();
                argumentos.putInt("idClienteParametro", idCliente);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fModificarCliente = new ModificarClienteFragment();
                fModificarCliente.setArguments(argumentos);
                //Lo enviamos al Fragment de ModificarProductos
                transaction.replace(R.id.fragment_container_view, fModificarCliente);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        }).setNegativeButton(null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



                clienteAdaptador.notifyDataSetChanged();

            }
        }).show();
    }
}