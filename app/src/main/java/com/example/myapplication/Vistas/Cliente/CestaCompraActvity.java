package com.example.myapplication.Vistas.Cliente;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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

import com.example.myapplication.Controlador.CarritoAdapter;
import com.example.myapplication.Controlador.CestaCompraDBHelper;
import com.example.myapplication.Controlador.ProductoCarrito;
import com.example.myapplication.Modelos.tbProducto;
import com.example.myapplication.R;
import com.example.myapplication.Vistas.UsuarioActivity;

import java.util.List;

public class CestaCompraActvity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cestacompra);


// Obtener productos de la base de datos y pasarlos a un adaptador
        CestaCompraDBHelper dbHelper = new CestaCompraDBHelper(getApplicationContext());
        //List<ProductoCarrito> productos = dbHelper.getAllProductos();
        // CarritoAdapter adapter = new CarritoAdapter(productos);
        //recyclerView.setAdapter(adapter);


        // Crear el cuadro de diálogo y obtener el layout personalizado
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_compra, null);

// Obtener las vistas del layout personalizado
        TextView nombreText = dialogView.findViewById(R.id.tv_nombre);
        TextView descripcionText = dialogView.findViewById(R.id.tv_descripcion);
        TextView precioText = dialogView.findViewById(R.id.tv_precio);
        EditText cantidadEditText = dialogView.findViewById(R.id.et_cantidad);

// Configurar el contenido de las vistas con los datos del producto
        nombreText.setText(getIntent().getStringExtra("nombreProducto"));
        descripcionText.setText(getIntent().getStringExtra("descripcionProducto"));
        precioText.setText(getIntent().getStringExtra("precio"));


// Crear el cuadro de diálogo con el layout personalizado
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setPositiveButton("Agregar al carrito", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                String nombre = nombreText.getText().toString();
                String descripcion = descripcionText.getText().toString();
                String precioString = precioText.getText().toString().replaceAll("[^\\d.]", "");
                String imagen = getIntent().getStringExtra("imagen");
                boolean estado = getIntent().getBooleanExtra("estado", false);
                double precio = Double.parseDouble(precioString);
                int cantidad = Integer.parseInt(cantidadEditText.getText().toString());

                CestaCompraDBHelper dbHelper = new CestaCompraDBHelper(getApplicationContext());
                long newRowId = dbHelper.insertProducto(nombre, precio, cantidad,imagen,estado);

                // Hacer algo con los valores obtenidos
                // Obtener productos de la base de datos y pasarlos a un adaptador


// Obtener productos de la base de datos y pasarlos a un adaptador
                //List<ProductoCarrito> listaProductos = new tbProducto().getListaFrutas();                CarritoAdapter adapter = new CarritoAdapter(productos);
                // recyclerView.setAdapter(adapter);


            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
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
