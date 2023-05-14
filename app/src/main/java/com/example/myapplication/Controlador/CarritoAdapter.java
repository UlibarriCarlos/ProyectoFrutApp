package com.example.myapplication.Controlador;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.android.material.internal.ThemeEnforcement;

import java.io.File;
import java.util.List;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.ViewHolder> {
    private List<ListElement> mData;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public CarritoAdapter(List<ListElement> itemList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }
    private static Drawable getDrawableByName(Context context, String nombreImagen) {
        int resId = context.getResources().getIdentifier(nombreImagen, "drawable", context.getPackageName());
        return context.getResources().getDrawable(resId);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_element, null);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));

        // Agrega el OnClickListener a la vista raíz del elemento
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(position);
                }
            }
        });
    }


    public void setItems(List<ListElement> items) {
        mData = items;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView nombre, descripcion, precio;

        ViewHolder(View itemView) {
            super(itemView);

            iconImage = itemView.findViewById(R.id.iconImageView);
            nombre = itemView.findViewById(R.id.nombre);
            descripcion = itemView.findViewById(R.id.descripcion);
            precio = itemView.findViewById(R.id.precio_Venta);
        }


        void bindData(final ListElement item) {
            //Añadido Drawable para ver iconos
            Drawable cn = getDrawableByName(context, item.getImagen());
            iconImage.setImageDrawable(cn);
            nombre.setText(item.getNombreProducto());
            descripcion.setText(item.getDescripcion());
            if (item.getEstado()) {
                precio.setText(item.getPrecio()+" €/Kg");
            }else {
                precio.setText(item.getPrecio()+" €/Und");
            }


        }
    }
}
