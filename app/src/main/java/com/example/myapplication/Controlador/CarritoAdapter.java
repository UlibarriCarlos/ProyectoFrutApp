package com.example.myapplication.Controlador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder> {
    private List<ProductoCarrito> productos;

    public CarritoAdapter(List<ProductoCarrito> productos) {
        this.productos = productos;
    }


    @NonNull
    @Override
    public CarritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carrito, parent, false);
        return new CarritoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoViewHolder holder, int position) {
        ProductoCarrito producto = productos.get(position);
        holder.nombreTextView.setText(producto.getNombre());
        holder.precioTextView.setText(String.format("%.2f €", producto.getPrecio()));
        holder.cantidadTextView.setText(String.valueOf(producto.getCantidad()));
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public static class CarritoViewHolder extends RecyclerView.ViewHolder {
        public TextView nombreTextView;
        public TextView precioTextView;
        public TextView cantidadTextView;

        public CarritoViewHolder(View view) {
            super(view);
            nombreTextView = view.findViewById(R.id.nombre_text_view);
            precioTextView = view.findViewById(R.id.precio_text_view);
            cantidadTextView = view.findViewById(R.id.cantidad_text_view);
        }
    }
}