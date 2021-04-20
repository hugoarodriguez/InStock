package com.example.instock.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instock.ListCategorias;
import com.example.instock.R;

import java.util.List;

public class CategoriasAdaptador extends RecyclerView.Adapter<CategoriasAdaptador.ViewHolder> {
    private List<ListCategorias> categoriasList;
    private Context context;

    public CategoriasAdaptador(List<ListCategorias> categoriasList, Context context) {
        this.categoriasList = categoriasList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_categorias, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtCategorias.setText(categoriasList.get(position).getCategorias());
    }

    @Override
    public int getItemCount() {
        return categoriasList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgDelete;
        private TextView txtCategorias;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDelete = itemView.findViewById(R.id.img_delete);
            txtCategorias = itemView.findViewById(R.id.tvCategoria);
        }
    }
}
