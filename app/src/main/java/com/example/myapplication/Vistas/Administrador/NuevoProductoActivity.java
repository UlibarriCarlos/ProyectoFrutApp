package com.example.myapplication.Vistas.Administrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Modelos.tbProducto;
import com.example.myapplication.R;
import com.example.myapplication.Vistas.UsuarioActivity;

public class NuevoProductoActivity extends AppCompatActivity {

    private EditText editTextCodigoTienda;
    private EditText editTextNombreProducto;
    private EditText editTextPrecio;
    private EditText editTextCantidad;
    private EditText editTextDescripcion;
    private ToggleButton toggleButtonEstado;
    private RadioGroup radioGroupCategoria;
    private Button buttonGuardarProducto;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nuevoproducto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Login:
                Intent intent1 = new Intent(NuevoProductoActivity.this, UsuarioActivity.class);
                startActivity(intent1);
                finish();
                return true;
            case R.id.Salir:
                finishAffinity(); // Cierra todas las actividades
                System.exit(0); // Cierra el proceso de la aplicación
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevoproducto);

        editTextCodigoTienda = findViewById(R.id.editTextCodigoTienda);
        editTextNombreProducto = findViewById(R.id.editTextNombreProducto);
        editTextPrecio = findViewById(R.id.editTextPrecio);
        editTextCantidad = findViewById(R.id.editTextCantidad);
        editTextDescripcion = findViewById(R.id.editTextDescripcion);
        toggleButtonEstado = findViewById(R.id.toggleButtonEstado);
        radioGroupCategoria = findViewById(R.id.radioGroupCategoria);
        buttonGuardarProducto = findViewById(R.id.buttonGuardarProducto);

        buttonGuardarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores de los campos
                int categoria = obtenerCategoriaSeleccionada();
                String codigoTienda = editTextCodigoTienda.getText().toString();
                String nombreProducto = editTextNombreProducto.getText().toString();
                double precio = Double.parseDouble(editTextPrecio.getText().toString());
                int cantidad = Integer.parseInt(editTextCantidad.getText().toString());
                String descripcion = editTextDescripcion.getText().toString();
                boolean estado = toggleButtonEstado.isChecked();

                // Guardar el producto (código a completar)
                tbProducto nuevo = new tbProducto();
                nuevo.crearProductoNuevo(categoria, codigoTienda, nombreProducto, precio, cantidad, descripcion, estado);

                // Mostrar Toast y cerrar la actividad
                Toast.makeText(getApplicationContext(), "Producto guardado", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private int obtenerCategoriaSeleccionada() {
        int categoriaId = radioGroupCategoria.getCheckedRadioButtonId();
        switch (categoriaId) {
            case R.id.radioButtonFrutas:
                return 1;
            case R.id.radioButtonVerduras:
                return 2;
            case R.id.radioButtonVarios:
                return 3;
            default:
                return 0;
        }
    }


}
