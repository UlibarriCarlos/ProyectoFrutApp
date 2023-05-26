package com.example.myapplication.Vistas.Cliente;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import static com.example.myapplication.Controlador.CestaCompraDBHelper.COLUMN_CANTIDAD;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.Controlador.CestaCompraDBHelper;
import com.example.myapplication.Controlador.Email;
import com.example.myapplication.Modelos.tbClientes;
import com.example.myapplication.Controlador.UsuarioGlobal;
import com.example.myapplication.R;
import com.example.myapplication.Vistas.PrincipalActivity;
import com.example.myapplication.Vistas.UsuarioActivity;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

public class FacturaActivity extends AppCompatActivity {
    private PDFView pdfView;
    private Button btnGenerarPdf;
    private String tituloText = "Su pedido ha sido tramitado:\n" +
            "Gracias por confiar en nosotros.";
    private String nombreCliente;
    private String direccionCliente;
    private String telefonoCliente;
    private String emailCliente;
    private String directoryPath;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_factura, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Frutas:
                //Iniciamos la nueva actividad
                Intent intentFrutas = new Intent(FacturaActivity.this, VerdurasActivity.class);
                startActivity(intentFrutas);
                break;
            case R.id.Verduras:
                //Iniciamos la nueva actividad
                Intent intentVerduras = new Intent(FacturaActivity.this, VerdurasActivity.class);
                startActivity(intentVerduras);
                break;
            case R.id.Varios:
                //Iniciamos la nueva actividad
                Intent intentVarios = new Intent(FacturaActivity.this, VariosActivity.class);
                startActivity(intentVarios);
                break;
            case R.id.Login:
                //Iniciamos la nueva actividad
                Intent intentAnadir = new Intent(FacturaActivity.this, UsuarioActivity.class);
                startActivity(intentAnadir);
                break;
            case R.id.Salir:
                finishAffinity(); // Cierra todas las actividades
                System.exit(0); // Cierra el proceso de la aplicación
                break;
        }
        return super.onOptionsItemSelected(item);
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
        nombreCliente = cliente.getNombre();
        direccionCliente = cliente.getDireccion();
        telefonoCliente = cliente.getTelefono();
        emailCliente = cliente.getEmail();

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
                String lineaImporte = String.format("%.2f", importe);

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
        String totalText = "Importe total: " + String.format("%.2f", importeTotal) + " €";
        canvas.drawText(totalText, 900, y + 50, descripcion);

        pdfDocument.finishPage(page);

        // Crear un archivo para el PDF
        // Obtén la ruta del directorio Documents
        //directoryPath = Environment.getExternalStorageDirectory().getPath();
        // directoryPath = Environment.getExternalStorageDirectory().getPath() + "/storage/emulated/0/Documents/";
        directoryPath = Environment.getExternalStorageDirectory().getPath() + "/Documents/";

// Crea un objeto File con la ruta del directorio
        File directory = new File(directoryPath);

// Verifica si el directorio no existe
        if (!directory.exists()) {
            // Intenta crear el directorio
            boolean isDirectoryCreated = directory.mkdirs();

            if (!isDirectoryCreated) {
                // Si no se pudo crear el directorio, muestra un mensaje de error o realiza alguna otra acción
                Toast.makeText(this, "Error al crear el directorio", Toast.LENGTH_SHORT).show();
                return;
            }
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

        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_compra, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        pdfDocument.close();
        btnGenerarPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// Crear el cuadro de diálogo con el layout personalizado
                // Crear el cuadro de diálogo y obtener el layout personalizado
                AlertDialog.Builder confirmDialogBuilder = new AlertDialog.Builder(FacturaActivity.this);
                confirmDialogBuilder.setMessage("¿Estás seguro de que quieres confirmar este pedido?");
                confirmDialogBuilder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Lógica para eliminar el producto de la base de datos y actualizar la lista
                        // Enviar el PDF por correo electrónico

                        Email enviarCorreo = new Email();
                        String destinatario = emailCliente;  // Reemplaza con el correo electrónico del destinatario
                        String asunto = "Pedido FrutApp";
                        String texto = "Su pedido está siendo preparado por nuestro equipo.\n " +
                                " Te avisaremos cuando esté listo para que puedas pasar a recogerlo por nuestra tienda.";
                        String adjuntoRuta = directoryPath + "Ticket.pdf"; // O puedes simplemente omitir el parámetro adjuntoRuta

                        boolean correoEnviado = enviarCorreo.enviarCorreo(destinatario, asunto, texto, adjuntoRuta);
                        if (correoEnviado) {
                            Toast.makeText(FacturaActivity.this, "Correo electrónico enviado", Toast.LENGTH_SHORT).show();
                            CestaCompraDBHelper dbHelper = new CestaCompraDBHelper(getApplicationContext());
                            dbHelper.borrarTodosProductos();
                            Intent intent1 = new Intent(FacturaActivity.this, FinCompraActivity.class);
                            startActivity(intent1);
                        } else {
                            Toast.makeText(FacturaActivity.this, "Error al enviar el correo electrónico", Toast.LENGTH_SHORT).show();
                        }

                        dialog.dismiss();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog confirmDialog = confirmDialogBuilder.create();
                confirmDialog.show();


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

