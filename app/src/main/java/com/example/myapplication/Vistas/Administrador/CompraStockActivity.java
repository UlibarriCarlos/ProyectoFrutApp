package com.example.myapplication.Vistas.Administrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.Vistas.Cliente.FrutasActivity;
import com.example.myapplication.Vistas.Cliente.VariosActivity;
import com.example.myapplication.Vistas.Cliente.VerdurasActivity;

public class CompraStockActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comprastock);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // Ocultar el título de la ActionBar
        /// getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }
}
