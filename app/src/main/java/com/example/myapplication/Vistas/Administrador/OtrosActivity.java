package com.example.myapplication.Vistas.Administrador;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class OtrosActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otros);

        getSupportActionBar().setDisplayShowTitleEnabled(false); // Ocultar el título de la ActionBar
        /// getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
