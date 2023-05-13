package com.example.myapplication.Controlador;

public class ProductoCarrito {
    private String nombre;
    private double precio;
    private int cantidad;

    public ProductoCarrito(String nombre, double precio, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }
}
