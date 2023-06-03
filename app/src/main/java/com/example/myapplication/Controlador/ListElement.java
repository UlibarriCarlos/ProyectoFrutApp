package com.example.myapplication.Controlador;
public class ListElement {
    public String nombreProducto;
    public String Descripcion;
    public String Precio;
    public String Imagen;
    public Boolean Estado;

    public ListElement(String nombreProducto, String Descripcion, String Precio, String Imagen, Boolean Estado) {
        this.nombreProducto = nombreProducto;
        this.Descripcion = Descripcion;
        this.Precio = Precio;
        this.Imagen = Imagen;
        this.Estado = Estado;
    }

    public ListElement() {
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

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String Precio) {
        this.Precio = Precio;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String Imagen) {
        this.Imagen = Imagen;
    }

    public Boolean getEstado() {
        return Estado;
    }

    public void setEstado(Boolean Estado) {
        this.Estado = Estado;
    }
}
