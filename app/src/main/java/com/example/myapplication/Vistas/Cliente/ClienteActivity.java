package com.example.myapplication.Vistas.Cliente;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class ClienteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cliente);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // Ocultar el título de la ActionBar
        /// getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
