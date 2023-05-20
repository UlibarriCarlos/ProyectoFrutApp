package com.example.myapplication.Vistas.Cliente;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
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

import com.example.myapplication.Controlador.GenerarPDF;
import com.example.myapplication.R;

import java.io.File;
import java.io.FileOutputStream;

public class FacturaActivity extends AppCompatActivity {

    private Button btnGenerarPdf;

    private String tituloText = "Este es el titulo del documento";

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
       boolean prueba= checkPermission();
        if (prueba) {
            Toast.makeText(this, "Permiso Aceptado", Toast.LENGTH_LONG).show();
        } else {
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

    public void generarPdf() {
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        TextPaint titulo = new TextPaint();
        TextPaint descripcion = new TextPaint();

        Bitmap bitmap, bitmapEscala;

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(816, 1054, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.verduras);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80, 80, false);
        canvas.drawBitmap(bitmapEscala, 368, 20, paint);

        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(20);
        canvas.drawText(tituloText, 10, 150, titulo);

        descripcion.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        descripcion.setTextSize(14);

        String[] arrDescripcion = descripcionText.split("\n");
        int y = 200;
        for (String line : arrDescripcion) {
            canvas.drawText(line, 10, y, descripcion);
            y += 15;
        }

        pdfDocument.finishPage(page);
        String filePath = Environment.getExternalStorageDirectory().getPath() + "/Documents/";
        File directory = new File(filePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, "Ticket2.pdf");
        ///File file = new File(Environment.getExternalStorageDirectory(), "Archivo.pdf");
        try {
                       pdfDocument.writeTo(new FileOutputStream(file));
           // pdfDocument.writeTo(new FileOutputStream(file, false));
            Toast.makeText(this, "Se creó el PDF correctamente", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
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

