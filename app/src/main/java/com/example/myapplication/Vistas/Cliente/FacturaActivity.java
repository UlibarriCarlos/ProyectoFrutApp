package com.example.myapplication.Vistas.Cliente;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import static com.example.myapplication.Controlador.CestaCompraDBHelper.COLUMN_CANTIDAD;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.Controlador.CestaCompraDBHelper;
import com.example.myapplication.Modelos.tbClientes;
import com.example.myapplication.Controlador.UsuarioGlobal;
import com.example.myapplication.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

public class FacturaActivity extends AppCompatActivity {

    private Button btnGenerarPdf;

    private String tituloText = "Su pedido ha sido tramitado, aqui tiene el importe:";

    private String descripcionText = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. \n" +
            "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, \n" +
            "when an unknown printer took a galley of type and scrambled it to make a type specimen book. \n" +
            "It has survived not only five centuries, but also the leap into electronic typesetting, \n" +
            "remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset\n" +
            " sheets containing Lorem Ipsum passages, and more recently with desktop publishing software\n" +
            " like Aldus PageMaker including versions of Lorem Ipsum.\n";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);




        if (!checkPermission()) {
            requestPermissions();
        }

        btnGenerarPdf = findViewById(R.id.btnFactura);

        btnGenerarPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generarPdf();
            }
        });
    }
    @SuppressLint("Range")
    public void generarPdf() {
        // Crear un nuevo PdfDocument
        PdfDocument pdfDocument = new PdfDocument();

        // Inicializar pinturas y estilos de texto
        Paint paint = new Paint();
        TextPaint titulo = new TextPaint();
        TextPaint encabezado = new TextPaint();
        TextPaint descripcion = new TextPaint();

        // Crear bitmaps para las imágenes
        Bitmap bitmap, bitmapEscala;

        // Crear PageInfo para la página del PDF
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(816, 1054, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        // Obtener el canvas de la página
        Canvas canvas = page.getCanvas();

        // Dibujar imágenes y texto
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.verduras);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80, 80, false);
        canvas.drawBitmap(bitmapEscala, 368, 20, paint);

        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(20);
        canvas.drawText(tituloText, 10, 150, titulo);

        encabezado.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        encabezado.setTextSize(14);

        descripcion.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        descripcion.setTextSize(14);
        CestaCompraDBHelper dbHelper = new CestaCompraDBHelper(getApplicationContext());

        tbClientes control = null;
        UsuarioGlobal myApp = (UsuarioGlobal) getApplicationContext();
        String NombreUsuario = myApp.getGlobalString();
        // Obtener el cliente
        tbClientes cliente = null;
        try {
            cliente = control.obtenerClientePorNombre(NombreUsuario);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

// Obtener los datos del cliente
        String nombreCliente = cliente.getNombre();
        String direccionCliente = cliente.getDireccion();
        String telefonoCliente = cliente.getTelefono();
        String emailCliente = cliente.getEmail();

// Dibujar los datos del cliente en el PDF
        canvas.drawText("Nombre: " + nombreCliente, 500, 100, descripcion);
        canvas.drawText("Dirección: " + direccionCliente, 500, 130, descripcion);
        canvas.drawText("Teléfono: " + telefonoCliente, 500, 160, descripcion);
        canvas.drawText("Email: " + emailCliente, 500, 190, descripcion);


        // Obtener los productos de la base de datos
        Cursor cursor = dbHelper.getAllProductos();

        double importeTotal = 0;
        int y = 200;

        // Dibujar el encabezado de las columnas
        String encabezadoNombre = "Nombre Producto";
        String encabezadoCantidad = "Cantidad";
        String encabezadoImporte = "Importe";
        canvas.drawText(encabezadoNombre, 10, y, encabezado);
        canvas.drawText(encabezadoCantidad, 200, y, encabezado);
        canvas.drawText(encabezadoImporte, 300, y, encabezado);
        y += 5;

        // Dibujar una línea divisoria
        canvas.drawLine(10, y, 350, y, paint);
        y += 20;

        // Recorrer el cursor y dibujar el texto para cada producto
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(CestaCompraDBHelper.COLUMN_ID));
                String nombreTicket = cursor.getString(cursor.getColumnIndex(CestaCompraDBHelper.COLUMN_NOMBRE));
                double precioTicket = cursor.getDouble(cursor.getColumnIndex(CestaCompraDBHelper.COLUMN_PRECIO));
                String imagenTicket = cursor.getString(cursor.getColumnIndex(CestaCompraDBHelper.COLUMN_IMAGEN));
                boolean estadoTicket = cursor.getInt(cursor.getColumnIndex(CestaCompraDBHelper.COLUMN_ESTADO)) == 1;
                int cantidadTicket = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CANTIDAD));

                // Calcular el importe y actualizar el importe total
                double importe = precioTicket * cantidadTicket;
                importeTotal += importe;

                // Formatear el texto para cada producto
                String unidades = estadoTicket ? " Kg" : " Uds";
                String lineaNombre = nombreTicket;
                String lineaCantidad = String.valueOf(cantidadTicket) + unidades;
                String lineaImporte = String.valueOf(importe);

                // Dibujar las líneas del producto en el canvas
                canvas.drawText(lineaNombre, 10, y, descripcion);
                canvas.drawText(lineaCantidad, 200, y, descripcion);
                canvas.drawText(lineaImporte + " €", 300, y, descripcion);
                y += 15;
            } while (cursor.moveToNext());
        }

        // Cerrar el cursor y dbHelper
        cursor.close();
        dbHelper.close();

        // Dibujar el importe total
        String importeTotalTexto = "Importe Total: " + importeTotal + " €";
        canvas.drawText(importeTotalTexto, 10, y + 20, descripcion);

        pdfDocument.finishPage(page);

        // Crear un archivo para el PDF
        String filePath = Environment.getExternalStorageDirectory().getPath() + "/Documents/";
        File directory = new File(filePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File file = new File(directory, "Ticket.pdf");

        try {
            // Escribir el contenido del PDF en el archivo
            FileOutputStream outputStream = new FileOutputStream(file);
            pdfDocument.writeTo(outputStream);
            outputStream.close();

            Toast.makeText(this, "Se creó el PDF correctamente", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        pdfDocument.close();
    }



    private boolean checkPermission() {
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 200);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 200) {
            if (grantResults.length > 0) {
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permiso concedido", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }
}

