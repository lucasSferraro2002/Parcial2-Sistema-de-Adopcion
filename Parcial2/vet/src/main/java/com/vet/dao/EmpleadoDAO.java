package com.vet.dao;

import com.vet.modelo.Empleado;
import com.vet.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    // Crear (registrar) un nuevo empleado
    public boolean crear(Empleado empleado) {
        String sql = "INSERT INTO empleados (nombre, apellido, username, password) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, empleado.getNombre());
            pstmt.setString(2, empleado.getApellido());
            pstmt.setString(3, empleado.getUsername());
            pstmt.setString(4, empleado.getPassword());

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                // Obtener el ID generado
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        empleado.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al crear empleado: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // Buscar empleado por username
    public Empleado buscarPorUsername(String username) {
        String sql = "SELECT * FROM empleados WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Empleado(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("username"),
                            rs.getString("password")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar empleado: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    // Buscar empleado por ID
    public Empleado buscarPorId(int id) {
        String sql = "SELECT * FROM empleados WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Empleado(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar empleado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar solo el ResultSet y PreparedStatement, NO la conexión
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    // Validar login (username y password)
    public Empleado validarLogin(String username, String password) {
        Empleado empleado = buscarPorUsername(username);

        if (empleado != null && empleado.getPassword().equals(password)) {
            return empleado;
        }

        return null;
    }

    // Verificar si existe un username
    public boolean existeUsername(String username) {
        return buscarPorUsername(username) != null;
    }

    // Obtener todos los empleados
    public List<Empleado> obtenerTodos() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleados";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Empleado empleado = new Empleado(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("username"),
                        rs.getString("password")
                );
                empleados.add(empleado);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener empleados: " + e.getMessage());
            e.printStackTrace();
        }

        return empleados;
    }

    // Actualizar empleado
    public boolean actualizar(Empleado empleado) {
        String sql = "UPDATE empleados SET nombre = ?, apellido = ?, username = ?, password = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, empleado.getNombre());
            pstmt.setString(2, empleado.getApellido());
            pstmt.setString(3, empleado.getUsername());
            pstmt.setString(4, empleado.getPassword());
            pstmt.setInt(5, empleado.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar empleado: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // Eliminar empleado
    public boolean eliminar(int id) {
        String sql = "DELETE FROM empleados WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar empleado: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}