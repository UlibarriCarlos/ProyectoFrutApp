package com.example.myapplication.Vistas.Administrador;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Controlador.ListAdapter;
import com.example.myapplication.Controlador.ListElement;
import com.example.myapplication.Modelos.tbProducto;
import com.example.myapplication.R;
import com.example.myapplication.Vistas.Cliente.CestaCompraActvity;
import com.example.myapplication.Vistas.Cliente.FrutasActivity;
import com.example.myapplication.Vistas.Cliente.VariosActivity;
import com.example.myapplication.Vistas.UsuarioActivity;

import java.util.ArrayList;
import java.util.List;

public class StockVerdurasActivity extends AppCompatActivity {

    private  List<ListElement> elements;
    private ListAdapter listAdapter;
    private  List<ListElement> elementos = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stockverduras);
        init();
        //vList<tbProducto> productos = tbProducto.obtenerProductos();
        // Crear una instancia de ProductAdapter y pasar la lista de productos

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_stockverduras, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Frutas:
                //Iniciamos la nueva actividad
                Intent intentFrutas = new Intent(StockVerdurasActivity.this, StockFrutasActivity.class);
                startActivity(intentFrutas);
                break;
            case R.id.Varios:
                //Iniciamos la nueva actividad
                Intent intentVarios = new Intent(StockVerdurasActivity.this, StockVariosActivity.class);
                startActivity(intentVarios);

                break;
            case R.id.Login:
                //Iniciamos la nueva actividad
                Intent intentAnadir = new Intent(StockVerdurasActivity.this, UsuarioActivity.class);
                startActivity(intentAnadir);
                break;
            case R.id.Salir:
                finishAffinity(); // Cierra todas las actividades
                System.exit(0); // Cierra el proceso de la aplicación
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void init() {

        List<tbProducto> listaProductos = new tbProducto().getListaVerduras();

        Log.v("listaProductos", String.valueOf(listaProductos.size()));

        List<ListElement> elementos = new ArrayList<>();
        for (tbProducto producto : listaProductos) {
            String unidad="";
            if (producto.getEstado()){ unidad=" Kg";}

            else{ unidad=" Uds";}
            elementos.add(new ListElement(producto.getNombreProducto(), String.valueOf(producto.getCantidad())+unidad, String.valueOf(producto.getPrecio()), producto.getImagen(), producto.getEstado()));
        }
         listAdapter = new ListAdapter(elementos, this);
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);

        listAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Obtener el elemento seleccionado
                ListElement elemento = elementos.get(position);
                String nombreProducto = elemento.nombreProducto;
                String cantidad = elemento.getDescripcion();
                String precio = elemento.getPrecio();

                mostrarDialogoCantidadPrecio(nombreProducto, cantidad, precio);
            }
        });


    }
    private void mostrarDialogoCantidadPrecio(String nombreProducto, String cantidad, String precio) {
        AlertDialog.Builder builder = new AlertDialog.Builder(StockVerdurasActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_stockcompra, null);

        TextView tvNombre = dialogView.findViewById(R.id.tv_nombre);
        EditText etCantidad = dialogView.findViewById(R.id.et_cantidad);
        EditText etPrecio = dialogView.findViewById(R.id.et_precio);

        tvNombre.setText(nombreProducto);
        etCantidad.setText(cantidad);
        etPrecio.setText(precio);

        builder.setView(dialogView)
                .setTitle("Modificar cantidad y precio")
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Obtener los nuevos valores de cantidad y precio
                        String NombreProducto = tvNombre.getText().toString();
                        int nuevaCantidad = Integer.parseInt(etCantidad.getText().toString());
                        Double nuevaPrecio = Double.parseDouble( etPrecio.getText().toString());
                        // Guardar los nuevos valores en la base de datos del producto
                        tbProducto cambio =new tbProducto();
                        cambio.modificarProducto(nuevaCantidad,nuevaPrecio,NombreProducto);


                        // Actualizar el adaptador con los datos actualizados
                        List<tbProducto> listaProductosActualizada = new tbProducto().getListaVerduras();
                        elementos.clear();
                        for (tbProducto producto : listaProductosActualizada) {
                            String unidad = producto.getEstado() ? " Kg" : " Uds";
                            elementos.add(new ListElement(producto.getNombreProducto(), String.valueOf(producto.getCantidad()) + unidad, String.valueOf(producto.getPrecio()), producto.getImagen(), producto.getEstado()));
                        }
                        listAdapter.notifyDataSetChanged();

                    }
                })
                .setNegativeButton("Cancelar", null)
                .create()
                .show();
    }



}
