package com.example.myapplication.Vistas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.Vistas.Cliente.FrutasActivity;
import com.example.myapplication.Vistas.Cliente.VariosActivity;
import com.example.myapplication.Vistas.Cliente.VerdurasActivity;

public class PrincipalActivity extends AppCompatActivity {
    private Button btnFrutas;
    private Button btnVerduras;
    private Button btnVarios;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);
        btnFrutas = findViewById(R.id.btnFrutas);
        btnVerduras = findViewById(R.id.btnVerduras);
        btnVarios = findViewById(R.id.btnVarios);

        btnFrutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(PrincipalActivity.this, FrutasActivity.class);
                startActivity(intent1);
            }
        }); btnVerduras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(PrincipalActivity.this, VerdurasActivity.class);
                startActivity(intent2);
            }
        });btnVarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(PrincipalActivity.this, VariosActivity.class);
                startActivity(intent3);
            }
        });

    }
}