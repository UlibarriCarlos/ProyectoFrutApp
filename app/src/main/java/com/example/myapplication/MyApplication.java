package com.example.myapplication;

import android.app.Application;

public class MyApplication extends Application {
    private String globalString;

    public String getGlobalString() {
        return globalString;
    }

    public void setGlobalString(String value) {
        globalString = value;
    }
}