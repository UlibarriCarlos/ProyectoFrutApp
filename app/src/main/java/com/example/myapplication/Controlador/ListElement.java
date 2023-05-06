package com.example.myapplication.Controlador;

public class ListElement {
    public String nombreProducto;
    public String Descripcion;
    public String Precio;


    public ListElement( String nombreProducto, String Descripcion, String Precio) {
        this.nombreProducto = nombreProducto;
        this.Descripcion = Descripcion;
        this.Precio = Precio;
    }




    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String name) {
        this.nombreProducto = name;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String city) {
        this.Descripcion = city;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String Precio) {
        this.Precio = Precio;
    }
}
