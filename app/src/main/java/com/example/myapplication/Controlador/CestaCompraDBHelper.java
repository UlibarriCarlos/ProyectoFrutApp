package com.example.myapplication.Controlador;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CestaCompraDBHelper extends SQLiteOpenHelper {

    // Información de la tabla
    public static final String TABLE_NAME = "productos";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_PRECIO = "precio";
    public static final String COLUMN_IMAGEN = "imagen";

    public static final String COLUMN_ESTADO = "estado";
    public static final String COLUMN_CANTIDAD = "cantidad";


    // Nombre de la base de datos
    private static final String DATABASE_NAME = "CestaCompraDBHelper.db";
    // Versión de la base de datos
    private static final int DATABASE_VERSION = 1;

    // Sentencia SQL para crear la tabla
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NOMBRE + " TEXT," +
                    COLUMN_PRECIO + " REAL," +
                    COLUMN_IMAGEN + " TEXT," +
                    COLUMN_ESTADO + " INTEGER," + // Cambio de tipo de datos
                    COLUMN_CANTIDAD + " REAL)";

    public CestaCompraDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hacemos nada en esta implementación
    }

    // Función para insertar un nuevo producto
    public long insertProducto(String nombre, double precio, int cantidad, String imagen, boolean estado) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_PRECIO, precio);
        values.put(COLUMN_IMAGEN, imagen);
        values.put(COLUMN_ESTADO, estado); // Actualización de valor

        values.put(COLUMN_CANTIDAD, cantidad);

        long newRowId = db.insert(TABLE_NAME, null, values);

        db.close();

        return newRowId;
    }


    // Función para leer todos los productos
    public Cursor getAllProductos() {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                COLUMN_ID,
                COLUMN_NOMBRE,
                COLUMN_PRECIO,
                COLUMN_IMAGEN,
                COLUMN_ESTADO,
                COLUMN_CANTIDAD
        };

        Cursor cursor = db.query(
                TABLE_NAME, // Tabla a consultar
                projection, // Columnas a devolver
                null, // Columnas para la cláusula WHERE
                null, // Valores para la cláusula WHERE
                null, // GROUP BY
                null, // HAVING
                null // ORDER BY
        );

        return cursor;
    }

    // Función para obtener la cantidad de un producto por nombre
    public Cursor getProductoCantidad(String nombre) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                COLUMN_ID,
                COLUMN_CANTIDAD
        };

        String selection = COLUMN_NOMBRE + " = ?";
        String[] selectionArgs = {nombre};

        Cursor cursor = db.query(
                TABLE_NAME, // Tabla a consultar
                projection, // Columnas a devolver
                selection, // Columnas para la cláusula WHERE
                selectionArgs, // Valores para la cláusula WHERE
                null, // GROUP BY
                null, // HAVING
                null // ORDER BY
        );

        return cursor;
    }

    // Función para actualizar la cantidad de un producto por nombre
    public int actualizarCantidad(String nombre, int nuevaCantidad) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CANTIDAD, nuevaCantidad);

        String selection = COLUMN_NOMBRE + " = ?";
        String[] selectionArgs = {nombre};

        int count = db.update(
                TABLE_NAME,
                values,
                selection,
                selectionArgs);

        db.close();

        return count;
    }
    // Función para borrar un producto por nombre
    public int borrarProductoPorNombre(String nombre) {
        SQLiteDatabase db = getWritableDatabase();

        String selection = COLUMN_NOMBRE + " = ?";
        String[] selectionArgs = {nombre};

        int count = db.delete(
                TABLE_NAME,
                selection,
                selectionArgs);

        db.close();

        return count;
    }

    public LiveData<Cursor> getAllProductosLive() {
        MutableLiveData<Cursor> mutableData = new MutableLiveData<>();
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                COLUMN_ID,
                COLUMN_NOMBRE,
                COLUMN_PRECIO,
                COLUMN_IMAGEN,
                COLUMN_ESTADO,
                COLUMN_CANTIDAD
        };

        Cursor cursor = db.query(
                TABLE_NAME, // Tabla a consultar
                projection, // Columnas a devolver
                null, // Columnas para la cláusula WHERE
                null, // Valores para la cláusula WHERE
                null, // GROUP BY
                null, // HAVING
                null // ORDER BY
        );

        mutableData.setValue(cursor);

        return mutableData;
    }
    public int borrarTodosProductos() {
        SQLiteDatabase db = getWritableDatabase();

        int count = db.delete(TABLE_NAME, null, null);

        db.close();

        return count;
    }

}
