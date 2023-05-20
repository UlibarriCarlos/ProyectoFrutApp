package com.example.myapplication.Vistas.Cliente;

import static com.example.myapplication.Controlador.CestaCompraDBHelper.COLUMN_CANTIDAD;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Controlador.CestaCompraDBHelper;
import com.example.myapplication.Controlador.ListAdapter;
import com.example.myapplication.Controlador.ListElement;
import com.example.myapplication.R;
import com.example.myapplication.Vistas.UsuarioActivity;

import java.util.ArrayList;
import java.util.List;

public class CestaCompraActvity extends AppCompatActivity {


    //Elementos Diseño
    private TextView txtImporteTotal;
    private TextView nombreText;
    private TextView descripcionText;
    private TextView precioText;
    private EditText cantidadEditText;

    //Variables para BBDD Sqlite
    private String nombre;
    private String descripcion;
    private String precioString;
    private String imagen;
    private boolean estado;
    private double precio;
    private int cantidad;
    private long newRowId;

    //Variables para Recyclerview
    private String unidades;
    private double importeTotal;
    List<ListElement> elementos = new ArrayList<>();

    private int id;
    private String nombreTicket;
    private double precioTicket;
    private String imagenTicket;
    private boolean estadoTicket;
    private double cantidadTicket;
    private String importe;

    private Cursor cursor;

    private Button btnFactura;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cestacompra);

        //Obener Boton
        btnFactura = findViewById(R.id.btnFactura);


        // Crear el cuadro de diálogo y obtener el layout personalizado
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_compra, null);

        // Obtener las vistas del layout personalizado
        nombreText = dialogView.findViewById(R.id.tv_nombre);
        descripcionText = dialogView.findViewById(R.id.tv_descripcion);
        precioText = dialogView.findViewById(R.id.tv_precio);
        cantidadEditText = dialogView.findViewById(R.id.et_cantidad);

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


                refresca();


            }

        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                refresca();
            }
        });
        builder.show();

        ListAdapter listAdapter = new ListAdapter(elementos, this);
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);

        listAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Obtener los datos del producto seleccionado
                String nombre = elementos.get(position).getNombreProducto();
                String descripcion = elementos.get(position).getDescripcion();
                double precio = Double.parseDouble(elementos.get(position).getPrecio());
                double cantidad = 0;

                // Crear el cuadro de diálogo y obtener el layout personalizado
                LayoutInflater inflater = LayoutInflater.from(CestaCompraActvity.this);
                View dialogView = inflater.inflate(R.layout.dialog_compra, null);

                // Obtener las vistas del layout personalizado
                nombreText = dialogView.findViewById(R.id.tv_nombre);
                descripcionText = dialogView.findViewById(R.id.tv_descripcion);
                precioText = dialogView.findViewById(R.id.tv_precio);
                cantidadEditText = dialogView.findViewById(R.id.et_cantidad);

                // Configurar el contenido de las vistas con los datos del producto
                nombreText.setText(nombre);
                descripcionText.setText(descripcion);
                precioText.setText(String.format("%.2f €", precio));
                cantidadEditText.setText(String.valueOf(cantidad));

                // Crear el cuadro de diálogo con el layout personalizado
                AlertDialog.Builder builder = new AlertDialog.Builder(CestaCompraActvity.this);
                builder.setView(dialogView);
                builder.setPositiveButton("Guardar cambios", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Lógica para guardar los cambios en la base de datos y actualizar la lista

                        int cantidadGuardarCambios = (int) Double.parseDouble(String.valueOf(cantidadEditText.getText()));
                        String nombreGuardarCambios = (String) nombreText.getText();
                        CestaCompraDBHelper dbHelper = new CestaCompraDBHelper(getApplicationContext());
                        if (cantidadGuardarCambios==0){

                            AlertDialog.Builder confirmDialogBuilder = new AlertDialog.Builder(CestaCompraActvity.this);
                            confirmDialogBuilder.setMessage("¿Estás seguro de que quieres eliminar este producto?");
                            confirmDialogBuilder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Lógica para eliminar el producto de la base de datos y actualizar la lista
                                    String nombreEliminarProducto = (String) nombreText.getText();
                                    CestaCompraDBHelper dbHelper = new CestaCompraDBHelper(getApplicationContext());
                                    dbHelper.borrarProductoPorNombre(nombreEliminarProducto);
                                    dialog.dismiss();
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            AlertDialog confirmDialog = confirmDialogBuilder.create();
                            confirmDialog.show();

                        }else{
                            dbHelper.actualizarCantidad(nombreGuardarCambios, cantidadGuardarCambios);
                        }





                    }
                }).setNegativeButton("Eliminar producto", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Lógica para eliminar el producto de la base de datos y actualizar la lista


                        AlertDialog.Builder confirmDialogBuilder = new AlertDialog.Builder(CestaCompraActvity.this);
                        confirmDialogBuilder.setMessage("¿Estás seguro de que quieres eliminar este producto?");
                        confirmDialogBuilder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Lógica para eliminar el producto de la base de datos y actualizar la lista
                                String nombreEliminarProducto = (String) nombreText.getText();
                                CestaCompraDBHelper dbHelper = new CestaCompraDBHelper(getApplicationContext());
                                dbHelper.borrarProductoPorNombre(nombreEliminarProducto);
                                dialog.dismiss();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        AlertDialog confirmDialog = confirmDialogBuilder.create();
                        confirmDialog.show();
                    }
                }).setNeutralButton("Cancelar", null);
                builder.show();
            }
        });

        btnFactura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVerduras = new Intent(CestaCompraActvity.this, FacturaActivity.class);
                startActivity(intentVerduras);
            }
        });
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

    @SuppressLint("Range")
    public void refresca() {

        nombre = nombreText.getText().toString();
        descripcion = descripcionText.getText().toString();
        precioString = precioText.getText().toString().replaceAll("[^\\d.]", "");
        imagen = getIntent().getStringExtra("imagen");
        estado = getIntent().getBooleanExtra("estado", false);
        precio = Double.parseDouble(precioString);
        cantidad = Integer.parseInt(cantidadEditText.getText().toString());
        // Objeto base de datos
        CestaCompraDBHelper dbHelper = new CestaCompraDBHelper(getApplicationContext());

        //Verificar si hay ya un producto en la tabla

        cursor = dbHelper.getProductoCantidad(nombre);

        if (cursor != null && cursor.moveToFirst()) {
            int cantidadPrueba = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CANTIDAD));
            cursor.close();

            // Hacer algo con la cantidad del producto obtenida
            dbHelper.actualizarCantidad(nombre, cantidad + cantidadPrueba);


        } else {
            // El producto no existe en la base de datos
            // Obtener guardar de la base de datos
            newRowId = dbHelper.insertProducto(nombre, precio, cantidad, imagen, estado);
        }


        // Obtener productos de la base de datos
        cursor = dbHelper.getAllProductos();


        importeTotal = 0;
        // Mostrar los productos
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(CestaCompraDBHelper.COLUMN_ID));
                nombreTicket = cursor.getString(cursor.getColumnIndex(CestaCompraDBHelper.COLUMN_NOMBRE));
                precioTicket = cursor.getDouble(cursor.getColumnIndex(CestaCompraDBHelper.COLUMN_PRECIO));
                imagenTicket = cursor.getString(cursor.getColumnIndex(CestaCompraDBHelper.COLUMN_IMAGEN));
                estadoTicket = cursor.getInt(cursor.getColumnIndex(CestaCompraDBHelper.COLUMN_ESTADO)) == 1;
                cantidadTicket = cursor.getDouble(cursor.getColumnIndex(COLUMN_CANTIDAD));

                //En el log
                Log.d("CestaCompraActivity", "Producto: " + id + " " + nombreTicket + " " + precioTicket + " " + imagenTicket + " " + estadoTicket + " " + cantidadTicket);

                if (estadoTicket) {
                    unidades = " Kg";
                } else {
                    unidades = " Uds";
                }
                importe = String.valueOf(precioTicket * cantidadTicket);
                importeTotal = importeTotal + precioTicket * cantidadTicket;
                elementos.add(new ListElement(nombreTicket, String.valueOf(cantidadTicket + unidades), importe, imagenTicket, null));
            } while (cursor.moveToNext());
        }
        txtImporteTotal = findViewById(R.id.txtImporteTotal);
        txtImporteTotal.setText(String.valueOf(importeTotal + " €"));

        // Cerrar el cursor y el dbHelper
        cursor.close();
        dbHelper.close();
    }

}
