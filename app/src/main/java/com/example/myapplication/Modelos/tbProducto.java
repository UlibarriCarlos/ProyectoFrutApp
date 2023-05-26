package com.example.myapplication.Modelos;

import android.icu.text.DecimalFormat;

import com.example.myapplication.Controlador.ConexionBD;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class tbProducto {
    private int idProducto;

    private int idCategoria;
    private String codigoTienda;
    private String nombreProducto;
    private double precio;
    private int cantidad;
    private String descripcion;
    private String imagen;
    private boolean estado;

    public tbProducto() {
    }

    public tbProducto(int idProducto, int idCategoria, String codigoTienda, String nombreProducto, double precio, int cantidad, String descripcion, String imagen, boolean estado) {
        this.idProducto = idProducto;
        this.idCategoria = idCategoria;
        this.codigoTienda = codigoTienda;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.estado = estado;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getidCategoria() {
        return idCategoria;
    }

    public void setidCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCodigoTienda() {
        return codigoTienda;
    }

    public void setCodigoTienda(String codigoTienda) {
        this.codigoTienda = codigoTienda;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<tbProducto> getListaEntera() {
        List<tbProducto> productos = new ArrayList<>();
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            cn = ConexionBD.conexionBD();
            pst = cn.prepareStatement("SELECT * FROM tbProducto");
            rs = pst.executeQuery();
            while (rs.next()) {
                tbProducto producto = new tbProducto(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getBoolean(9));
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return productos;
    }

    public List<tbProducto> getListaFrutas() {
        List<tbProducto> productos = new ArrayList<>();
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            cn = ConexionBD.conexionBD();
            pst = cn.prepareStatement("SELECT * FROM tbProducto WHERE idCategoria = 1 AND stock > 0");
            rs = pst.executeQuery();
            while (rs.next()) {
                tbProducto producto = new tbProducto(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getBoolean(9));
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return productos;
    }

    public List<tbProducto> getListaVerduras() {
        List<tbProducto> productos = new ArrayList<>();
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            cn = ConexionBD.conexionBD();
            pst = cn.prepareStatement("SELECT * FROM tbProducto WHERE idCategoria = 2 AND stock > 0");
            rs = pst.executeQuery();
            while (rs.next()) {
                tbProducto producto = new tbProducto(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getBoolean(9));
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return productos;
    }
    public List<tbProducto> getListaVarios() {
        List<tbProducto> productos = new ArrayList<>();
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            cn = ConexionBD.conexionBD();
            pst = cn.prepareStatement("SELECT * FROM tbProducto WHERE idCategoria = 3 AND stock > 0");
            rs = pst.executeQuery();
            while (rs.next()) {
                tbProducto producto = new tbProducto(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getBoolean(9));
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return productos;
    }

    public List<tbProducto> getListaSoloNombreProducto() {
        List<tbProducto> productos = new ArrayList<>();
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            cn = ConexionBD.conexionBD();
            pst = cn.prepareStatement("SELECT * FROM tbProducto WHERE idCategoria = 3");
            rs = pst.executeQuery();
            while (rs.next()) {
                tbProducto producto = new tbProducto(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getBoolean(9));
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return productos;
    }


    // Método para guardar un registro
    public void guardarFrutas(String nombreProducto, double precio, int cantidad, String descripcion) throws SQLException {
        BigDecimal precioVenta = new BigDecimal(precio);
        String query = "INSERT INTO tbProducto(idCategoria, codigoTienda, nombre, precio_Venta, stock, descripcion, imagen, estado) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection connection = ConexionBD.conexionBD(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, 1);
            statement.setString(2, "F00");
            statement.setString(3, nombreProducto);
            statement.setBigDecimal(4, precioVenta);
            statement.setInt(5, cantidad);
            statement.setString(6, descripcion);
            statement.setString(7, "verduras");
            statement.setBoolean(8, true);
            statement.executeUpdate();
            ConexionBD.conexionBD().close();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }
    public void guardarVerduras(String nombreProducto, double precio, int cantidad, String descripcion) throws SQLException {
        BigDecimal precioVenta = new BigDecimal(precio);
        String query = "INSERT INTO tbProducto(idCategoria, codigoTienda, nombre, precio_Venta, stock, descripcion, imagen, estado) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection connection = ConexionBD.conexionBD(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, 2);
            statement.setString(2, "V00");
            statement.setString(3, nombreProducto);
            statement.setBigDecimal(4, precioVenta);
            statement.setInt(5, cantidad);
            statement.setString(6, descripcion);
            statement.setString(7, "verduras");
            statement.setBoolean(8, true);
            statement.executeUpdate();
            ConexionBD.conexionBD().close();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }
    public void guardarVarios(String nombreProducto, double precio, int cantidad, String descripcion) throws SQLException {
        BigDecimal precioVenta = new BigDecimal(precio);
        String query = "INSERT INTO tbProducto(idCategoria, codigoTienda, nombre, precio_Venta, stock, descripcion, imagen, estado) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection connection = ConexionBD.conexionBD(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, 3);
            statement.setString(2, "X00");
            statement.setString(3, nombreProducto);
            statement.setBigDecimal(4, precioVenta);
            statement.setInt(5, cantidad);
            statement.setString(6, descripcion);
            statement.setString(7, "verduras");
            statement.setBoolean(8, true);
            statement.executeUpdate();
            ConexionBD.conexionBD().close();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }

    public void modificarProducto( int nuevaCantidad, double nuevoPrecio,String nombreProducto) {
        Connection cn = null;
        PreparedStatement pst = null;

        try {
            cn = ConexionBD.conexionBD();
            pst = cn.prepareStatement("UPDATE tbProducto SET stock = ?, precio_Venta = ? WHERE nombre = ?");

            pst.setInt(1, nuevaCantidad);
            pst.setDouble(2, nuevoPrecio);
            pst.setString(3, nombreProducto);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) pst.close();
                if (cn != null) cn.close();
                ConexionBD.conexionBD().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void crearProductoNuevo(int nuevoCategoria, String nuevoCodigoTienda, String nuevoNombreProducto, double nuevoPrecio, int nuevoCantidad, String nuevoDescripcion, boolean nuevoEstado ) {

        // Guardar el nuevo producto en la base de datos
        Connection cn = null;
        PreparedStatement pst = null;

        try {
            cn = ConexionBD.conexionBD();
            pst = cn.prepareStatement("INSERT INTO tbProducto (idCategoria, codigoTienda, nombre, precio_Venta, stock, descripcion, imagen, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            pst.setInt(1, nuevoCategoria);
            pst.setString(2, nuevoCodigoTienda);
            pst.setString(3, nuevoNombreProducto);
            pst.setDouble(4, nuevoPrecio);
            pst.setInt(5, nuevoCantidad);
            pst.setString(6, nuevoDescripcion);
            pst.setString(7, "verduras");
            pst.setBoolean(8, nuevoEstado);

            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) pst.close();
                if (cn != null) cn.close();
                ConexionBD.conexionBD().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }




}
