package com.example.myapplication.Vistas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class CestaCompraActvity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cestacompra);
        // Crear el cuadro de diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Introducir datos de compra");

// Crear los campos de texto para introducir los datos de compra
        final TextView nombreText = new TextView(this);
        nombreText.setText(getIntent().getStringExtra("nombreProducto"));
        final TextView descripcionText = new TextView(this);
        descripcionText.setHint(getIntent().getStringExtra("descripcionProducto"));
        final TextView precioText = new TextView(this);
        precioText.setHint("Precio del producto");
        final EditText cantidadEditText = new EditText(this);
        precioText.setHint("Elige Cantidad");

// Agregar los campos de texto al cuadro de diálogo
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(nombreText);
        layout.addView(descripcionText);
        layout.addView(precioText);
        layout.addView(cantidadEditText);
        builder.setView(layout);

// Agregar los botones para aceptar y cancelar
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Obtener los valores introducidos por el usuario
                String nombre = nombreText.getText().toString();
                String descripcion = descripcionText.getText().toString();
                String precio = precioText.getText().toString();
                String cantidad =cantidadEditText.getText().toString();
                // Hacer algo con los valores obtenidos, como agregarlos a la lista de la cesta de la compra
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

// Mostrar el cuadro de diálogo
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cestacompra, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Frutas:
                //Iniciamos la nueva actividad
                Intent intentFrutas = new Intent(CestaCompraActvity.this, FrutasActivity.class);
                startActivity(intentFrutas);
                break;
            case R.id.Verduras:
                //Iniciamos la nueva actividad
                Intent intentVerduras = new Intent(CestaCompraActvity.this, VerdurasActivity.class);
                startActivity(intentVerduras);
                break;
            case R.id.Varios:
                //Iniciamos la nueva actividad

                Intent intentVarios = new Intent(CestaCompraActvity.this, VariosActivity.class);
                startActivity(intentVarios);
                break;
            case R.id.Login:
                //Iniciamos la nueva actividad
                Intent intentAnadir = new Intent(CestaCompraActvity.this, UsuarioActivity.class);
                startActivity(intentAnadir);
                break;
            case R.id.Salir:
                finishAffinity(); // Cierra todas las actividades
                System.exit(0); // Cierra el proceso de la aplicación
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
