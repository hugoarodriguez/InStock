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
import com.example.instock.models.ListaVentas;
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
        holder.tvFechaEntregadoVal.setText(ventasList.get(position).getFechaEntregado());
        holder.tvCateVal.setText(ventasList.get(position).getCategoria());
        holder.tvNomProVal.setText(ventasList.get(position).getNombrePro());
        holder.tvCantProVal.setText(ventasList.get(position).getCantidadPro());
        holder.tvPrecioProVal.setText("$" + ventasList.get(position).getTotalPago());
        holder.tvClienteProVal.setText(ventasList.get(position).getCliente());
        Glide.with(context).load(ventasList.get(position).getFotoProd()).into(holder.imgFoto);

    }
    @Override
    public int getItemCount() { return ventasList.size(); }


    public  static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgFoto;
        private TextView tvFechaEntregadoVal;
        private TextView tvCateVal;
        private TextView tvNomProVal;
        private TextView tvCantProVal;
        private TextView tvPrecioProVal;
        private TextView tvClienteProVal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFoto = itemView.findViewById(R.id.imgProd);
            tvFechaEntregadoVal = itemView.findViewById(R.id.tvFechaEntregadoVal);
            tvCateVal = itemView.findViewById(R.id.tvCateVal);
            tvNomProVal = itemView.findViewById(R.id.tvNomProVal);
            tvCantProVal = itemView.findViewById(R.id.tvCantProVal);
            tvPrecioProVal = itemView.findViewById(R.id.tvPrecioProVal);
            tvClienteProVal = itemView.findViewById(R.id.tvClienteProVal);
        }
    }
}
