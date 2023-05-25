package com.example.myapplication.Vistas.Administrador;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.Vistas.Cliente.FrutasActivity;
import com.example.myapplication.Vistas.Cliente.VariosActivity;
import com.example.myapplication.Vistas.Cliente.VerdurasActivity;
import com.example.myapplication.Vistas.UsuarioActivity;

public class StockPrincipalActivity extends AppCompatActivity {
    private Button btnFrutas;
    private Button btnVerduras;
    private Button btnVarios;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_stockprincipal, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Login:
                Intent intent1 = new Intent(StockPrincipalActivity.this, UsuarioActivity.class);
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
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stockprincipal);

        btnFrutas = findViewById(R.id.btnSalir);
        btnVerduras = findViewById(R.id.btnVerduras);
        btnVarios = findViewById(R.id.btnVarios);

        btnFrutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(StockPrincipalActivity.this, StockFrutasActivity.class);
                startActivity(intent1);
            }
        }); btnVerduras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(StockPrincipalActivity.this, StockVerdurasActivity.class);
                startActivity(intent2);
            }
        });btnVarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(StockPrincipalActivity.this, StockVariosActivity.class);
                startActivity(intent3);
            }
        });

    }
}