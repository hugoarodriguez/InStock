package com.example.instock.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instock.R;
import com.example.instock.models.Reserva;

import java.util.List;

public class ReservasAdaptador extends RecyclerView.Adapter<ReservasAdaptador.ViewHolder>{

    private List<Reserva> ReservaList;
    private Context context;

    public ReservasAdaptador(List<Reserva> reservaList, Context context) {
        ReservaList = reservaList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReservasAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta_reserva,parent,false);
        return new ReservasAdaptador.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservasAdaptador.ViewHolder holder, int position) {
        holder.txtProducto.setText(ReservaList.get(position).getNomProducto());
        holder.txtPrecio.setText("$" + ReservaList.get(position).getPrecio());
        holder.txtCliente.setText(ReservaList.get(position).getCliente());
        Glide.with(context).load(ReservaList.get(position).getFotoProd()).into(holder.imgProdu);
        holder.txtCantidad.setText(ReservaList.get(position).getCantProd());
    }
    @Override
    public int getItemCount() {
        return ReservaList.size();
    }

    public  static class ViewHolder extends RecyclerView.ViewHolder{


        private ImageView imgProdu;
        private TextView txtProducto;
        private TextView txtPrecio;
        private TextView txtCliente;
        private TextView txtCantidad;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProdu = itemView.findViewById(R.id.imgProdu);
            txtProducto = itemView.findViewById(R.id.tvProductoVal);
            txtPrecio = itemView.findViewById(R.id.tvPrecioVal);
            txtCliente = itemView.findViewById(R.id.tvClienteVal);
            txtCantidad = itemView.findViewById(R.id.tvCantidadVal);
        }
    }
}
