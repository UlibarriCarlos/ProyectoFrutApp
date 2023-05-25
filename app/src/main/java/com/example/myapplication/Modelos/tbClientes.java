package com.example.myapplication.Modelos;

import com.example.myapplication.Controlador.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class tbClientes {
    private int idCliente;
    private String nombreUsuario;
    private String DNI;
    private String direccion;
    private String telefono;
    private String email;
    private String contraseña;
    private boolean estado;

    public tbClientes() {
    }

    public tbClientes(int idCliente, String nombreUsuario, String DNI, String direccion, String telefono, String email, String contraseña, boolean estado) {
        this.idCliente = idCliente;
        this.nombreUsuario = nombreUsuario;
        this.DNI = DNI;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.contraseña = contraseña;
        this.estado = estado;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombreUsuario;
    }

    public void setNombre(String nombre) {
        this.nombreUsuario = nombre;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }


    public boolean getEstado() {
        return estado;
    }

    public tbClientes obtenerUltimoRegistro() throws SQLException {
        tbClientes cliente = null;
        String query = "SELECT  * FROM tbCliente ORDER BY idCliente DESC";
        try (Connection connection = ConexionBD.conexionBD(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                cliente = new tbClientes(
                        resultSet.getInt("idCliente"),
                        resultSet.getString("nombreUsuario"),
                        resultSet.getString("DNI"),
                        resultSet.getString("direccion"),
                        resultSet.getString("telefono"),
                        resultSet.getString("email"),
                        resultSet.getString("contraseña"),
                        resultSet.getBoolean("estado"));
            }
            ConexionBD.conexionBD().close();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return cliente;
    }

    public static tbClientes obtenerClientePorId(int idCliente) throws SQLException {
        tbClientes cliente = null;
        String query = "SELECT * FROM tbCliente WHERE idCliente = ?";
        try (Connection connection = ConexionBD.conexionBD(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idCliente);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    cliente = new tbClientes(
                            resultSet.getInt("idCliente"),
                            resultSet.getString("nombreUsuario"),
                            resultSet.getString("DNI"),
                            resultSet.getString("direccion"),
                            resultSet.getString("telefono"),
                            resultSet.getString("email"),
                            resultSet.getString("contraseña"),
                            resultSet.getBoolean("estado")
                    );
                }
            }
            ConexionBD.conexionBD().close();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return cliente;
    }

    public static tbClientes obtenerClientePorNombre(String nombreUsuario) throws SQLException {
        tbClientes cliente = null;
        String query = "SELECT * FROM tbCliente WHERE nombreUsuario = ?";
        try (Connection connection = ConexionBD.conexionBD(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nombreUsuario);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    cliente = new tbClientes(
                            resultSet.getInt("idCliente"),
                            resultSet.getString("nombreUsuario"),
                            resultSet.getString("DNI"),
                            resultSet.getString("direccion"),
                            resultSet.getString("telefono"),
                            resultSet.getString("email"),
                            resultSet.getString("contraseña"),
                            resultSet.getBoolean("estado")
                    );
                }
            }
            ConexionBD.conexionBD().close();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return cliente;
    }

    public static tbClientes obtenerClientePoremail(String email) throws SQLException {
        tbClientes cliente = null;
        String query = "SELECT * FROM tbCliente WHERE nombreUsuario = ?";
        try (Connection connection = ConexionBD.conexionBD(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    cliente = new tbClientes(
                            resultSet.getInt("idCliente"),
                            resultSet.getString("nombreUsuario"),
                            resultSet.getString("DNI"),
                            resultSet.getString("direccion"),
                            resultSet.getString("telefono"),
                            resultSet.getString("email"),
                            resultSet.getString("contraseña"),
                            resultSet.getBoolean("estado")
                    );
                }
            }
            ConexionBD.conexionBD().close();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return cliente;
    }

    public static tbClientes obtenerClientePorDNI(String DNI) throws SQLException {
        tbClientes cliente = null;
        String query = "SELECT * FROM tbCliente WHERE DNI = ?";
        try (Connection connection = ConexionBD.conexionBD(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, DNI);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    cliente = new tbClientes(
                            resultSet.getInt("idCliente"),
                            resultSet.getString("nombreUsuario"),
                            resultSet.getString("DNI"),
                            resultSet.getString("direccion"),
                            resultSet.getString("telefono"),
                            resultSet.getString("email"),
                            resultSet.getString("contraseña"),
                            resultSet.getBoolean("estado")
                    );
                }
            }
            ConexionBD.conexionBD().close();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return cliente;
    }

    // Método para guardar un registro
    public void guardar(String nombre, String DNI, String direccion, String telefono, String email, String contraseña) throws SQLException {

        String query = "INSERT INTO tbCliente(nombreUsuario, DNI, direccion, telefono, email, contraseña, estado) VALUES(?,?,?,?,?,?,?)";
        try (Connection connection = ConexionBD.conexionBD(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nombre);
            statement.setString(2, DNI);
            statement.setString(3, direccion);
            statement.setString(4, telefono);
            statement.setString(5, email);
            statement.setString(6, contraseña);
            statement.setBoolean(7, true);
            statement.executeUpdate();
            ConexionBD.conexionBD().close();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }

    public boolean nombreRepetido(String nombre) throws SQLException {
        String query = "SELECT COUNT(*) FROM tbCliente WHERE nombre = ?";
        try (Connection connection = ConexionBD.conexionBD(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nombre);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
            ConexionBD.conexionBD().close();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return false;
    }


}
