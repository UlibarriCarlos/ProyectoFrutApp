package com.example.myapplication.Vistas.Cliente;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Controlador.ListAdapter;
import com.example.myapplication.Controlador.ListElement;
import com.example.myapplication.Modelos.tbProducto;
import com.example.myapplication.R;
import com.example.myapplication.Vistas.UsuarioActivity;

import java.util.ArrayList;
import java.util.List;

public class VerdurasActivity extends AppCompatActivity {

    List<ListElement> elements;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verduras);
        init();
        //vList<tbProducto> productos = tbProducto.obtenerProductos();
        // Crear una instancia de ProductAdapter y pasar la lista de productos

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_verduras, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Frutas:
                //Iniciamos la nueva actividad
                Intent intentFrutas = new Intent(VerdurasActivity.this, FrutasActivity.class);
                startActivity(intentFrutas);
                break;
            case R.id.Varios:
                //Iniciamos la nueva actividad
                Intent intentVarios = new Intent(VerdurasActivity.this, VariosActivity.class);
                startActivity(intentVarios);

                break;
            case R.id.Login:
                //Iniciamos la nueva actividad
                Intent intentAnadir = new Intent(VerdurasActivity.this, UsuarioActivity.class);
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
            String color = "#607d8b";
            elementos.add(new ListElement(producto.getNombreProducto(), producto.getDescripcion(), String.valueOf(producto.getPrecio()),producto.getImagen(),producto.getEstado()));
        }
        ListAdapter listAdapter = new ListAdapter(elementos, this);
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);

        listAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Obtener el elemento seleccionado
                ListElement elementoSeleccionado = elementos.get(position);

                // Abrir el formulario de compra
                Intent intent = new Intent(VerdurasActivity.this, CestaCompraActvity.class);
                intent.putExtra("nombreProducto", elementoSeleccionado.getNombreProducto());
                intent.putExtra("descripcionProducto", elementoSeleccionado.getDescripcion());
                if (elementoSeleccionado.getEstado()) {
                    intent.putExtra("precio", elementoSeleccionado.getPrecio()+" €/Kg");
                }else {
                    intent.putExtra("precio", elementoSeleccionado.getPrecio()+" €/Und");
                }                startActivity(intent);
            }
        });

       /* elements = new ArrayList<>();
        elements.add(new ListElement("#775447", "", "", ""));
        elements.add(new ListElement("#607d8b", "", "", ""));
        elements.add(new ListElement("#03a9f4", "", "", ""));
        elements.add(new ListElement("#f44336", "", "", ""));
        elements.add(new ListElement("#009688", "", "", ""));

        ListAdapter listAdapter = new ListAdapter(elements, this);
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);*/
    }


}
