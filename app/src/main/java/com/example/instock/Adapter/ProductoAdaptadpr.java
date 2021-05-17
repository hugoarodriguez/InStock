package com.example.instock.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instock.R;
import com.example.instock.interfaces.RecyclerViewClickInterface;
import com.example.instock.models.Producto;

import java.util.List;

public class ProductoAdaptadpr extends  RecyclerView.Adapter<ProductoAdaptadpr.ViewHolder>{

    private List<Producto> ProductoList;
    private Context context;
    //Agregado
    private static RecyclerViewClickInterface recyclerViewClickInterface;

    public ProductoAdaptadpr(List<Producto> productoList, Context context, RecyclerViewClickInterface recyclerViewClickInterface) {
        ProductoList = productoList;
        this.context = context;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta_producto,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvProducto.setText(ProductoList.get(position).getNomProducto());
        holder.tvCategoria.setText(ProductoList.get(position).getCategoria());
        holder.tvCantidad.setText(ProductoList.get(position).getCantidad());
        holder.tvPrecio.setText(ProductoList.get(position).getPrecio());
        Glide.with(context).load(ProductoList.get(position).getFotoProd()).into(holder.imgFoto);
    }
    @Override
    public int getItemCount() {
        return ProductoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{


        private ImageView imgFoto;
        private TextView tvProducto;
        private TextView tvCategoria;
        private TextView tvCantidad;
        private TextView tvPrecio;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFoto = itemView.findViewById(R.id.imgProd);
            tvProducto = itemView.findViewById(R.id.tvNombreProductoVal);
            tvCategoria = itemView.findViewById(R.id.tvCategoriaVal);
            tvCantidad = itemView.findViewById(R.id.tvCantidadVal);
            tvPrecio = itemView.findViewById(R.id.tvPrecioVal);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
