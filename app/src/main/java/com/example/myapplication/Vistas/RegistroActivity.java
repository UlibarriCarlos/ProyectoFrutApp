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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Controlador.ControladorContraseñas;
import com.example.myapplication.Controlador.Email;
import com.example.myapplication.Modelos.tbClientes;
import com.example.myapplication.R;
import com.example.myapplication.Vistas.Cliente.FacturaActivity;
import com.example.myapplication.Vistas.Cliente.FinCompraActivity;

import java.sql.SQLException;

public class RegistroActivity extends AppCompatActivity {

    private EditText etNombreApellidos, etAlias, etDNI, etDireccion, etTelefono, etEmail1, etEmail2, etContraseña1, etContraseña2;
    private Button btnAltaUsuario;

    private Boolean comprobarAlias = false;
    private Boolean comprobarDNI = false;
    private Boolean comprobarContraseña = false;
    private Boolean comprobarEmail = false;

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
        setContentView(R.layout.activity_registro);

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

//Comprobacion si algun campo esta vacio, repetido o ya xiste en la BBDD
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
                            etAlias.setText(null);
                            etAlias.setHintTextColor(Color.RED);
                            etAlias.setHint("Alias ya existe, cambialo");
                            comprobarAlias = true;
                        } else {
                            etAlias.setTextColor(Color.WHITE);
                            comprobarAlias = false;
                        }
                        if (control.obtenerClientePorDNI(etDNI.getText().toString()) != null) {
                            etDNI.setText(null);
                            etDNI.setHintTextColor(Color.RED);
                            etDNI.setHint("DNI ya existe, cambialo");
                            comprobarDNI = true;
                        } else {
                            etAlias.setTextColor(Color.WHITE);
                            comprobarDNI = false;
                        }
                        if (etEmail1.getText().toString().equals(etEmail2.getText().toString())) {
                            comprobarAlias = false;
                            if (control.obtenerClientePoremail(etEmail1.getText().toString()) != null) {
                                etEmail1.setText(null);
                                etEmail2.setText(null);
                                etEmail1.setHintTextColor(Color.RED);
                                etEmail2.setHintTextColor(Color.RED);
                                etEmail1.setHint("Email ya existe, cambialo");
                                etEmail2.setHint("Email ya existe, cambialo");
                                comprobarEmail = true;
                            } else {
                                etAlias.setTextColor(Color.WHITE);
                                comprobarEmail = false;
                            }

                        } else {
                            etEmail1.setText(null);
                            etEmail2.setText(null);
                            etEmail1.setHintTextColor(Color.RED);
                            etEmail2.setHintTextColor(Color.RED);
                            etEmail1.setHint("Los emails no coinciden");
                            etEmail2.setHint("Los emails no coinciden");
                            comprobarEmail = true;
                        }
                        if (etContraseña1.getText().toString().equals(etContraseña2.getText().toString())) {
                            comprobarContraseña = false;
                        } else {
                            etContraseña1.setText(null);
                            etContraseña2.setText(null);
                            etContraseña1.setHintTextColor(Color.RED);
                            etContraseña2.setHintTextColor(Color.RED);
                            etContraseña1.setHint("Las contrseñas no coinciden");
                            etContraseña2.setHint("Los contrseñas no coinciden");
                            comprobarContraseña = true;
                        }

                    } catch (SQLException e) {
                        Log.e("Error onClick btn_loginUsuario", e.toString());
                        throw new RuntimeException(e);
                    }

                    if (comprobarAlias || comprobarDNI || comprobarContraseña || comprobarEmail) {

                        Toast.makeText(RegistroActivity.this, "Revisa los datos introducidos ", Toast.LENGTH_LONG).show();
                        comprobarAlias = false;
                        comprobarDNI = false;
                        comprobarContraseña = false;
                        comprobarEmail = false;

                    } else {
                        tbClientes cliente = new tbClientes();
                        try {


                            // Lógica para eliminar el producto de la base de datos y actualizar la lista

                            String destinatario =  etEmail1.getText().toString();  // Reemplaza con el correo electrónico del destinatario
                            Email enviarCorreo = new Email();
                            boolean correoEnviado = enviarCorreo.enviarCorreo(destinatario);

                            if (correoEnviado) {
                                //Encriptamos contraseña
                                String contraseñaEncriptada = ControladorContraseñas.encrypt(etContraseña1.getText().toString());
                                //Guardamos datos del cliente despues las comprobaciones
                                cliente.guardar(etAlias.getText().toString(), etDNI.getText().toString(), etDireccion.getText().toString(), etTelefono.getText().toString(), etEmail1.getText().toString(), contraseñaEncriptada);
                                Toast.makeText(RegistroActivity.this, "Los datos se han guardado correctamente, recibiras email de bienvenida", Toast.LENGTH_LONG).show();
                                Intent intent1 = new Intent(RegistroActivity.this, UsuarioActivity.class);
                                startActivity(intent1);
                            } else {
                                Toast.makeText(RegistroActivity.this, "Error al enviar el correo, revisalo", Toast.LENGTH_SHORT).show();
                                etEmail1.setText(null);
                                etEmail2.setText(null);
                                etEmail1.setHintTextColor(Color.RED);
                                etEmail2.setHintTextColor(Color.RED);
                                etEmail1.setHint("Los emails no validos");
                                etEmail2.setHint("Los emails no validos");
                            }

                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
            }
        });


    }


}