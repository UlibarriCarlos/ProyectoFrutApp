package com.example.myapplication.Vistas.Administrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Modelos.tbProducto;
import com.example.myapplication.R;
import com.example.myapplication.Vistas.RegistroActivity;
import com.example.myapplication.Vistas.UsuarioActivity;

public class AdministradorActivity extends AppCompatActivity {
    private Button btnCompraStock;
    private Button btnAltaProducto;
    private Button btnOtros;
    tbProducto producto = new tbProducto();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_administrador, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Login:
                Intent intent1 = new Intent(AdministradorActivity.this, UsuarioActivity.class);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);

        btnCompraStock = findViewById(R.id.btnCompraStock);
        btnAltaProducto = findViewById(R.id.btnAltaProducto);
        btnOtros = findViewById(R.id.btnOtros);

        btnCompraStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(AdministradorActivity.this, StockPrincipalActivity.class);
                startActivity(intent1);

            }
        });
        btnAltaProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(AdministradorActivity.this, NuevoProductoActivity.class);
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
