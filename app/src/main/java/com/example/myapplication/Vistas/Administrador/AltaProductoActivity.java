package com.example.myapplication.Vistas.Administrador;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Modelos.tbProducto;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class AltaProductoActivity extends AppCompatActivity {

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.altaproducto);

        // Obtener la lista de nombres de productos
        List<String> nombresProductos = new ArrayList<>();
        List<tbProducto> listaProductos = new tbProducto().getListaEntera();
        for (tbProducto producto : listaProductos) {
            nombresProductos.add(producto.getNombreProducto());
        }


        // Crear el ArrayAdapter y establecerlo como el adaptador del Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, nombresProductos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
    }
}

