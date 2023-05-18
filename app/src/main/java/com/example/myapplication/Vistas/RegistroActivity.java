package com.example.myapplication.Vistas;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Controlador.ControladorContraseñas;
import com.example.myapplication.Modelos.tbClientes;
import com.example.myapplication.R;

import java.sql.SQLException;

public class RegistroActivity extends AppCompatActivity {

    private EditText etNombreApellidos, etAlias, etDNI, etDireccion, etTelefono, etEmail1, etEmail2, etContraseña1, etContraseña2;
    private Button btnAltaUsuario;

    private Boolean datosRepetidos = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_frutas, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Login:
                Intent intent1 = new Intent(RegistroActivity.this, UsuarioActivity.class);
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        etNombreApellidos = findViewById(R.id.etNombreApellidos);
        etAlias = findViewById(R.id.etAlias);
        etDNI = findViewById(R.id.etDNI);
        etDireccion = findViewById(R.id.etDireccion);
        etTelefono = findViewById(R.id.etTelefono);
        etEmail1 = findViewById(R.id.etEmail1);
        etEmail2 = findViewById(R.id.etEmail2);
        etContraseña1 = findViewById(R.id.etContraseña1);
        etContraseña2 = findViewById(R.id.etContraseña2);
        btnAltaUsuario = findViewById(R.id.btnAltaUsuario);

        btnAltaUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("LogUsuario", "Boton Login usuario pulsado");


              /*  // Prueba para mostrar los datos del cliente obtenido en el Log
                Log.i("Cliente", "ID: " + cliente.getIdCliente());
                Log.i("Cliente", "Nombre: " + cliente.getNombre());
                Log.i("Cliente", "DNI: " + cliente.getDNI());
                Log.i("Cliente", "Dirección: " + cliente.getDireccion());
                Log.i("Cliente", "Teléfono: " + cliente.getTelefono());
                Log.i("Cliente", "Email: " + cliente.getEmail());
                Log.i("Cliente", "Contraseña: " + cliente.getContraseña());
                Log.i("Cliente", "Estado: " + cliente.getEstado());*/


                if (etNombreApellidos.getText().toString().equals("")
                        || etAlias.getText().toString().equals("")
                        || etDireccion.getText().toString().equals("")
                        || etDNI.getText().toString().equals("")
                        || etTelefono.getText().toString().equals("")
                        || etEmail1.getText().toString().equals("")
                        || etEmail2.getText().toString().equals("")
                        || etContraseña2.getText().toString().equals("")
                        || etContraseña1.getText().toString().equals("")) {
                    Toast.makeText(RegistroActivity.this, "Faltan datos de por introducir", Toast.LENGTH_LONG).show();
                } else {

                    try {
                        tbClientes control = null;
                        if (control.obtenerClientePorNombre(etAlias.getText().toString()) != null) {
                            etAlias.setTextColor(Color.RED);
                            datosRepetidos = true;
                        }
                        if (control.obtenerClientePorDNI(etDNI.getText().toString()) != null) {
                            etDNI.setTextColor(Color.RED);
                            datosRepetidos = true;
                        }
                        if (control.obtenerClientePoremail(etEmail1.getText().toString()) != null) {
                            etEmail1.setTextColor(Color.RED);
                            datosRepetidos = true;
                        }

                    } catch (SQLException e) {
                        Log.e("Error onClick btn_loginUsuario", e.toString());
                        throw new RuntimeException(e);
                    }

                    if (datosRepetidos) {

                        Toast.makeText(RegistroActivity.this, "Nombre, Email o DNI repetidos en BBDD revisa datos", Toast.LENGTH_LONG).show();
                        datosRepetidos = false;

                    } else {
                        tbClientes cliente = new tbClientes();
                        try {
                            String contraseñaEncriptada = ControladorContraseñas.encrypt(etContraseña1.getText().toString());
                            cliente.guardar(etAlias.getText().toString(), etDNI.getText().toString(), etDireccion.getText().toString(), etTelefono.getText().toString(), etEmail1.getText().toString(), etContraseña1.getText().toString());

                            Toast.makeText(RegistroActivity.this, "Los datos se han guardado correctamente, recibiras email para activarlo", Toast.LENGTH_LONG).show();
                            Intent intent1 = new Intent(RegistroActivity.this, UsuarioActivity.class);
                            startActivity(intent1);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
            }
        });


    }


}