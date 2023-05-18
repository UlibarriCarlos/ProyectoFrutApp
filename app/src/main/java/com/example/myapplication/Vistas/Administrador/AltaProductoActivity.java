package com.example.myapplication.Vistas.Administrador;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;


public class AltaProductoActivity extends AppCompatActivity {

    private Button btnFrutas;
    private Button btnVerduras;
    private Button btnVarios;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.altaproducto);

        // Obtener la lista de nombres de productos
       // List<String> nombresProductos = new ArrayList<>();
       // List<tbProducto> listaProductos = new tbProducto().getListaEntera();
      //  for (tbProducto producto : listaProductos) {
       //     nombresProductos.add(producto.getNombreProducto());
      //  }


        // Crear el ArrayAdapter y establecerlo como el adaptador del Spinner

        btnFrutas = findViewById(R.id.btnAltaFrutas);
        btnVerduras = findViewById(R.id.btnAltaVerduras);
        btnVarios = findViewById(R.id.btnAltaVarios);

        btnFrutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AltaProductoActivity.this, AltaFrutasActivity.class);
                startActivity(intent1);
            }
        }); btnVerduras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(AltaProductoActivity.this, AltaVerdurasActivity.class);
                startActivity(intent2);
            }
        });btnVarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(AltaProductoActivity.this, AltaVariosActivity.class);
                startActivity(intent3);
            }
        });

    }
}
