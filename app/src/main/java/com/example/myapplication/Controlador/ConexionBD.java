package com.example.myapplication.Controlador;

import android.os.StrictMode;
import android.util.Log;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ConexionBD {
    // Atributos de la clase
    private Connection conexion;
    private Statement stm;

    // Métodos de la clase
    public static Connection conexionBD() {
        Connection cnn = null;
        try {
            StrictMode.ThreadPolicy politica = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(politica);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            cnn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.0.137;databaseName=FrutApp;user=sa;password=sa;"); //Portatil
            //cnn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.52.1;databaseName=FrutApp;user=2222;password=1234;"); //PC
        } catch (Exception e) {
            Log.e("Error conexionBD", e.toString());
            throw new RuntimeException(e);
        }
        return cnn;

    }

    public ResultSet Listar(String sql) {
        ResultSet rs = null;
        try {
            stm = conexionBD().createStatement();
            rs = stm.executeQuery(sql);
        } catch (SQLException e) {
            Log.v("Error BuscarMostrar", e.toString());
            throw new RuntimeException(e);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                Log.e("Error Listar", e.toString());
                throw new RuntimeException(e);
            }
        }
        return rs;
    }
}


