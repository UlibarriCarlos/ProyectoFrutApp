package com.example.myapplication.Controlador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Modelos.tbProducto;
import com.example.myapplication.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<tbProducto> listaProductos;

    public ProductAdapter(List<tbProducto> productList) {
        this.listaProductos = productList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frutas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        tbProducto product = listaProductos.get(position);
        holder.nombreProductos.setText(product.getNombreProducto());
        holder.precioProductos.setText((int) product.getPrecio());
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nombreProductos;
        public TextView precioProductos;

        public ViewHolder(View itemView) {
            super(itemView);

            //nombreProductos = (TextView) itemView.findViewById(R.id.nombreProductos);
            // precioProductos = (TextView) itemView.findViewById(R.id.precioProductos);
        }
    }
}