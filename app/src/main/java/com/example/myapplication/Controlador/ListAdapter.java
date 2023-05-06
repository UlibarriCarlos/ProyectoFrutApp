package com.example.myapplication.Controlador;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ListElement> mData;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public ListAdapter(List<ListElement> itemList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
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
           // iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            nombre.setText(item.getNombreProducto());
            descripcion.setText(item.getDescripcion());
            precio.setText(item.getPrecio());
        }
    }
}
