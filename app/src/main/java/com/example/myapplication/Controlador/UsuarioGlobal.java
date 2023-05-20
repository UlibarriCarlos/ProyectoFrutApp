package com.example.myapplication.Controlador;

import android.app.Application;

public class UsuarioGlobal extends Application {
    private String globalString;

    public String getGlobalString() {
        return globalString;
    }

    public void setGlobalString(String value) {
        globalString = value;
    }
}