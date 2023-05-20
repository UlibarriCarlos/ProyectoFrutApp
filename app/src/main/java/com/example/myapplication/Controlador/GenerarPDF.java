package com.example.myapplication.Controlador;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.text.TextPaint;
import android.widget.Toast;
import android.content.Context;
import com.example.myapplication.R;

import java.io.File;
import java.io.FileOutputStream;

public class GenerarPDF {


    public void GuardarPdf(Context context) {
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        TextPaint titulo = new TextPaint();
        TextPaint descripcion = new TextPaint();

        Bitmap bitmap, bitmapEscala;

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(816, 1054, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.verduras);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80, 80, false);
        canvas.drawBitmap(bitmapEscala, 368, 20, paint);

        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(20);
        canvas.drawText("Hola", 10, 150, titulo);

        descripcion.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        descripcion.setTextSize(14);
        String hola = "hola \n";
        String[] arrDescripcion = hola.split("\n");
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

        File file = new File(directory, "Archivo.pdf");
        //File file = new File(Environment.getExternalStorageDirectory(), "Archivo.pdf");
        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            // Toast.makeText(this, "Se creó el PDF correctamente", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        pdfDocument.close();
    }
}
