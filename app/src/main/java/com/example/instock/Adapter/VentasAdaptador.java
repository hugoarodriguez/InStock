package com.example.instock.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instock.ListaClientes;
import com.example.instock.ListaVentas;
import com.example.instock.R;

import java.util.List;

public class VentasAdaptador extends RecyclerView.Adapter<VentasAdaptador.ViewHolder>{

    private List<ListaVentas> ventasList;
    private Context context;

    public VentasAdaptador(List<ListaVentas> ventasList, Context context) {
        this.ventasList = ventasList;
        this.context = context;
    }


    @NonNull
    @Override
    public VentasAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_ventas,parent,false);
        return new VentasAdaptador.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VentasAdaptador.ViewHolder holder, int position) {
        holder.txtCodigo.setText(ventasList.get(position).getCodigo());
        holder.txtCategoria.setText(ventasList.get(position).getCategoria());
        holder.txtNombrePro.setText(ventasList.get(position).getNombrePro());
        holder.txtCantidadPro.setText(ventasList.get(position).getCantidadPro());
        holder.txtPrecioPro.setText(ventasList.get(position).getPrecioPro());
        holder.txtCliente.setText(ventasList.get(position).getCliente());

    }
    @Override
    public int getItemCount() { return ventasList.size(); }


    public  static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgFoto;
        private TextView txtCodigo;
        private TextView txtCategoria;
        private TextView txtNombrePro;
        private TextView txtCantidadPro;
        private TextView txtPrecioPro;
        private TextView txtCliente;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFoto = itemView.findViewById(R.id.imgProd);
            txtCodigo = itemView.findViewById(R.id.tvCod);
            txtCategoria = itemView.findViewById(R.id.tvCate);
            txtNombrePro = itemView.findViewById(R.id.tvNomPro);
            txtCantidadPro = itemView.findViewById(R.id.tvCantPro);
            txtPrecioPro = itemView.findViewById(R.id.tvPrecioPro);
            txtCliente = itemView.findViewById(R.id.tvClientePro);
        }
    }
}
