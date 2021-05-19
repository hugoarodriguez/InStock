package com.example.instock.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instock.models.ListaClientes;
import com.example.instock.R;
import java.util.List;

public class ClientesAdaptador extends RecyclerView.Adapter<ClientesAdaptador.ViewHolder>{

    private List<ListaClientes> clientesList;
    private Context context;

    public ClientesAdaptador(List<ListaClientes> clientesList, Context context) {
        this.clientesList = clientesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_clientes,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNombre.setText(clientesList.get(position).getNombre());
        holder.txtTelefono.setText(clientesList.get(position).getTelefono());
        holder.txtCorreo.setText(clientesList.get(position).getCorreo());
        holder.txtSexo.setText(clientesList.get(position).getSexo());

        if(!clientesList.get(position).getSexo().trim().toUpperCase().equals("MASCULINO")){

            Drawable img = (Drawable)context.getResources().getDrawable(R.drawable.ic_outline_account_circle_24_pink);
            holder.imgvUsuario.setImageDrawable(img);
        } else{
            Drawable img = (Drawable)context.getResources().getDrawable(R.drawable.ic_outline_account_circle_24);
            holder.imgvUsuario.setImageDrawable(img);
        }
    }

    @Override
    public int getItemCount() {
        return clientesList.size();
    }

    public  static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgvUsuario;
        private TextView txtNombre;
        private TextView txtTelefono;
        private TextView txtCorreo;
        private TextView txtSexo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgvUsuario = itemView.findViewById(R.id.imgvUsuario);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtTelefono = itemView.findViewById(R.id.txtTelefono);
            txtCorreo = itemView.findViewById(R.id.txtCorreo);
            txtSexo = itemView.findViewById(R.id.txtSexo);
        }
    }
}
