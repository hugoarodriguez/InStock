package com.example.instock.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instock.AcercaDe;
import com.example.instock.R;
import com.example.instock.models.ListaDesarrolladores;

import java.util.List;


public class DesarrolladorAdapter extends RecyclerView.Adapter<DesarrolladorAdapter.ViewHolder>{
   private List<ListaDesarrolladores> devList;
   private Context context;

   public DesarrolladorAdapter(List<ListaDesarrolladores> devList, Context context)
   {
       this.context = context;
       this.devList = devList;
   }



    @Override
    public int getItemCount() {
        return devList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lista_devs, null);
       return new ViewHolder(view);

        /*View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_devs,parent,false);
        return new DesarrolladorAdapter.ViewHolder(view);*/
    }

    @Override
    public void onBindViewHolder(@NonNull DesarrolladorAdapter.ViewHolder holder, int position) {
        holder.bindData(devList.get(position));
    }

    public void setItems(List<ListaDesarrolladores> item)
    {
        devList = item;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView devImage;
        private TextView nombreDev, carnetDev;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            devImage = itemView.findViewById(R.id.imgDev);
            nombreDev = itemView.findViewById(R.id.tvDesarrolladorVal);
            carnetDev = itemView.findViewById(R.id.tvDevCarnetVal);
        }

        void bindData(final ListaDesarrolladores item)
        {
            devImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            nombreDev.setText(item.getNombre());
            carnetDev.setText(item.getCarnet());
        }

    }


}
