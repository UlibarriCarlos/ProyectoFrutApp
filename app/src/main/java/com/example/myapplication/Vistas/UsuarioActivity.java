package com.example.myapplication.Vistas;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Controlador.ControladorContraseñas;
import com.example.myapplication.Modelos.tbClientes;
import com.example.myapplication.Controlador.UsuarioGlobal;
import com.example.myapplication.R;
import com.example.myapplication.Vistas.Administrador.AdministradorActivity;
import com.example.myapplication.Vistas.Cliente.PrincipalActivity;

import java.sql.SQLException;


public class UsuarioActivity extends AppCompatActivity {
    private EditText et_alias, et_contraseña;
    private Button btn_loginUsuario;
    private TextView tv_registrarse;


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
        setContentView(R.layout.activity_usuario);


        //Login
        et_alias = findViewById(R.id.etUsuario);
        et_contraseña = findViewById(R.id.etPassword);
        btn_loginUsuario = findViewById(R.id.btnLogin);
        tv_registrarse = findViewById(R.id.tvSignUp);

        btn_loginUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Registramos acciones click en Logcat
                Log.i("LogUsuario", "Boton Login usuario pulsado");

                try {


                    cliente = cliente.obtenerClienteAlias(et_alias.getText().toString());
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
                if (et_alias.getText().toString().equals("") || et_contraseña.getText().toString().equals("")) {
                    Toast.makeText(UsuarioActivity.this, "No has introducido usuario o contraseña", Toast.LENGTH_LONG).show();
                } else {
                    if (cliente == null) {
                        Toast.makeText(UsuarioActivity.this, "Error al introducir usuario o contraseña", Toast.LENGTH_LONG).show();

                    } else {
                        if (cliente.getAlias().equals(et_alias.getText().toString()) && cliente.getContraseña().equals(ControladorContraseñas.encrypt(et_contraseña.getText().toString())) && et_alias.getText().toString().equals("admin") && cliente.getContraseña().equals(ControladorContraseñas.encrypt(et_contraseña.getText().toString())) && cliente.getEstado() == true) {
                            Intent intent1 = new Intent(UsuarioActivity.this, AdministradorActivity.class);
                            startActivity(intent1);
                        } else if (cliente.getAlias().equals(et_alias.getText().toString()) && cliente.getContraseña().equals(ControladorContraseñas.encrypt(et_contraseña.getText().toString())) && cliente.getEstado() == true) {

                            //Guardo en variable global el nombre del usuario
                            UsuarioGlobal myApp = (UsuarioGlobal) getApplicationContext();
                            myApp.setGlobalString(cliente.getNombre());
                            Intent intent1 = new Intent(UsuarioActivity.this, PrincipalActivity.class);
                            startActivity(intent1);

                        } else if (cliente.getAlias().equals(et_alias.getText().toString()) && cliente.getContraseña().equals(et_contraseña.getText().toString()) && cliente.getEstado() == false) {
                            Toast.makeText(UsuarioActivity.this, "Usuario activado.", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(UsuarioActivity.this, "Usuario y/o contraseña erróneo", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                if (cliente != null) {
                    // La conexión fue exitosa
                    Log.v("Conexion", "Conexión satisfactoria");
                } else {
                    // La conexión falló
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





