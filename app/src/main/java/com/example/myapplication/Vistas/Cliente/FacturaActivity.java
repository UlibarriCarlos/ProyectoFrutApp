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
import android.graphics.Color;
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
import com.example.myapplication.Controlador.Email;
import com.example.myapplication.Modelos.tbClientes;
import com.example.myapplication.Controlador.UsuarioGlobal;
import com.example.myapplication.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

public class FacturaActivity extends AppCompatActivity {
    private PDFView pdfView;
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
        pdfView = findViewById(R.id.pdfView);
        generarPdf();

    }

    @SuppressLint("Range")
    public void generarPdf() {
        // Crear un nuevo PdfDocument
        PdfDocument pdfDocument = new PdfDocument();

        // Crear PageInfo para la página del PDF
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1632, 2116, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        // Obtener el canvas de la página
        Canvas canvas = page.getCanvas();

        // Dibujar imágenes y texto
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.verduras);
        Bitmap bitmapEscala = Bitmap.createScaledBitmap(bitmap, 160, 160, false);
        Paint paint = new Paint();
        canvas.drawBitmap(bitmapEscala, 736, 40, paint);

        TextPaint titulo = new TextPaint();
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(40);
        canvas.drawText(tituloText, 20, 300, titulo);

        TextPaint encabezado = new TextPaint();
        encabezado.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        encabezado.setTextSize(40);

        TextPaint descripcion = new TextPaint();
        descripcion.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        descripcion.setTextSize(40);

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
        canvas.drawText("Nombre: " + nombreCliente, 1000, 200, descripcion);
        canvas.drawText("Dirección: " + direccionCliente, 1000, 260, descripcion);
        canvas.drawText("Teléfono: " + telefonoCliente, 1000, 320, descripcion);
        canvas.drawText("Email: " + emailCliente, 1000, 380, descripcion);

        // Obtener los productos de la base de datos
        Cursor cursor = dbHelper.getAllProductos();

        double importeTotal = 0;
        int y = 500;

        // Dibujar el encabezado de las columnas
        String encabezadoNombre = "Nombre Producto";
        String encabezadoCantidad = "Cantidad";
        String encabezadoImporte = "Importe";
        canvas.drawText(encabezadoNombre, 20, y, encabezado);
        canvas.drawText(encabezadoCantidad, 500, y, encabezado);
        canvas.drawText(encabezadoImporte, 900, y, encabezado);
        y += 10;

        // Dibujar una línea divisoria
        paint.setColor(Color.BLACK);
        canvas.drawLine(20, y, 1550, y, paint);
        y += 50;

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
                canvas.drawText(lineaNombre, 20, y, descripcion);
                canvas.drawText(lineaCantidad, 500, y, descripcion);
                canvas.drawText(lineaImporte + " €", 900, y, descripcion);
                y += 40;
            } while (cursor.moveToNext());
        }

        // Cerrar el cursor y dbHelper
        cursor.close();
        dbHelper.close();

        // Dibujar el importe total
        String totalText = "Importe total: " + String.valueOf(importeTotal) + " €";
        canvas.drawText(totalText, 900, y + 50, descripcion);

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
            // Mostrar el PDF en el visor
            pdfView.fromFile(file).load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        pdfDocument.close();
        btnGenerarPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Enviar el PDF por correo electrónico
                // Enviar el PDF por correo electrónico
                String destinatario = "uliferio@gmail.com";  // Reemplaza con el correo electrónico del destinatario
               Email enviarCorreo = new Email();
                boolean correoEnviado =   enviarCorreo.enviarCorreo(destinatario);

                if (correoEnviado) {
                    Toast.makeText(FacturaActivity.this, "Correo electrónico enviado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FacturaActivity.this, "Error al enviar el correo electrónico", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

