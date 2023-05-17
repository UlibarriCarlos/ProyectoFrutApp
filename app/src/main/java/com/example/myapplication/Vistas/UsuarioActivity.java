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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Controlador.ControladorContraseñas;
import com.example.myapplication.Controlador.Email;
import com.example.myapplication.Modelos.tbClientes;
import com.example.myapplication.R;
import com.example.myapplication.Vistas.Administrador.AdministradorActivity;

import java.sql.SQLException;


public class UsuarioActivity extends AppCompatActivity {
    private EditText et_nombre, et_contraseña;
    private Button btn_loginUsuario;
    private TextView tv_registrarse;
    private TextView textView;

    // Llamar al método obtenerUltimoRegistro()
    tbClientes cliente = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_usuario, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.Salir:
                finishAffinity(); // Cierra todas las actividades
                System.exit(0); // Cierra el proceso de la aplicación
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario);

        getSupportActionBar().setDisplayShowTitleEnabled(true); // Ocultar el título de la ActionBar
       // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // Hacer la ActionBar transparente
        //Login
        et_nombre = findViewById(R.id.etNombreApellidos);
        et_contraseña = findViewById(R.id.etPassword);
        btn_loginUsuario = findViewById(R.id.btnLogin);
        tv_registrarse = findViewById(R.id.tvSignUp);

        btn_loginUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Registarmos acciones click en Logcat
                Log.i("LogUsuario", "Boton Login usuario pulsado");

                try {

                    cliente = cliente.obtenerClientePorNombre(et_nombre.getText().toString());
                } catch (SQLException e) {
                    Log.e("Error onClick btn_loginUsuario", e.toString());
                    throw new RuntimeException(e);
                }

              /*  // Prueba para mostrar los datos del cliente obtenido en el Log
                Log.i("Cliente", "ID: " + cliente.getIdCliente());
                Log.i("Cliente", "Nombre: " + cliente.getNombre());
                Log.i("Cliente", "DNI: " + cliente.getDNI());
                Log.i("Cliente", "Dirección: " + cliente.getDireccion());
                Log.i("Cliente", "Teléfono: " + cliente.getTelefono());
                Log.i("Cliente", "Email: " + cliente.getEmail());
                Log.i("Cliente", "Contraseña: " + cliente.getContraseña());
                Log.i("Cliente", "Estado: " + cliente.getEstado());*/

                //Verificacion de usuario
                if (et_nombre.getText().toString().equals("") || et_contraseña.getText().toString().equals("")) {
                    Toast.makeText(UsuarioActivity.this, "No has intrducido usuario o contraseña", Toast.LENGTH_LONG).show();
                } else {
                    if (cliente == null) {
                        Toast.makeText(UsuarioActivity.this, "No existe usuario o contraseña mal introducida", Toast.LENGTH_LONG).show();

                    } else {
                        if (cliente.getNombre().equals(et_nombre.getText().toString()) && cliente.getContraseña().equals(et_contraseña.getText().toString()) && et_nombre.getText().toString().equals("admin") && et_contraseña.getText().toString().equals("admin") && cliente.getEstado() == true) {
                            Intent intent1 = new Intent(UsuarioActivity.this, AdministradorActivity.class);
                            startActivity(intent1);
                        } else if (cliente.getNombre().equals(et_nombre.getText().toString()) && cliente.getContraseña().equals(et_contraseña.getText().toString()) && cliente.getEstado() == true) {
                        //} else if (cliente.getNombre().equals(et_nombre.getText().toString()) && cliente.getContraseña().equals(ControladorContraseñas.encrypt(et_contraseña.getText().toString())) && cliente.getEstado() == true) {
                            Intent intent1 = new Intent(UsuarioActivity.this, PrincipalActivity.class);
                            startActivity(intent1);
                            Email correo = new Email();
                            //correo.enviarCorreo("uliferio@gmail.com");

                        } else if (cliente.getNombre().equals(et_nombre.getText().toString()) && cliente.getContraseña().equals(et_contraseña.getText().toString()) && cliente.getEstado() == false) {
                            Toast.makeText(UsuarioActivity.this, "Tienes que activar usuario, mira tu correo", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(UsuarioActivity.this, "Usuarioy/o contraseña erroneo", Toast.LENGTH_LONG).show();
                        }
                    }
                }


                if (cliente != null) {
                    // La conexión fue exitosa
                    //Toast.makeText(UsuarioActivity.this, "Conexión satisfactoria", Toast.LENGTH_LONG).show();
                    Log.v("Conexion", "Conexión satisfactoria");

                } else {
                    // La conexión falló
                    //Toast.makeText(UsuarioActivity.this, "Conexión Fallida", Toast.LENGTH_LONG).show();
                    Log.v("Conexion", "Conexión fallida");
                }


            }

        });
        tv_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_registrarse.setTextColor(Color.GREEN);
                Intent intent1 = new Intent(UsuarioActivity.this, RegistroActivity.class);
                startActivity(intent1);
            }
        });

    }


}





