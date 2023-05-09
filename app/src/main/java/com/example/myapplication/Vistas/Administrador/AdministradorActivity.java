package com.example.myapplication.Vistas.Administrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Modelos.tbProducto;
import com.example.myapplication.R;
import com.example.myapplication.Vistas.Cliente.FrutasActivity;
import com.example.myapplication.Vistas.Cliente.VerdurasActivity;
import com.example.myapplication.Vistas.Cliente.VariosActivity;

import java.sql.SQLException;

public class AdministradorActivity extends AppCompatActivity {
    private Button btnCompraStock;
    private Button btnAltaProducto;
    private Button btnOtros;
tbProducto producto=new tbProducto();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administrador);

        btnCompraStock = findViewById(R.id.btnCompraStock);
        btnAltaProducto = findViewById(R.id.btnAltaProducto);
        btnOtros = findViewById(R.id.btnOtros);

        btnCompraStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    producto.guardarFrutas( "Nectarina",15.64, 150, "Fresquitas");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Intent intent1 = new Intent(AdministradorActivity.this, CompraStockActivity.class);
                startActivity(intent1);

            }
        });
        btnAltaProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(AdministradorActivity.this, AltaProductoActivity.class);
                startActivity(intent2);
            }
        });
        btnOtros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(AdministradorActivity.this, OtrosActivity.class);
                startActivity(intent3);
            }
        });
    }
}
