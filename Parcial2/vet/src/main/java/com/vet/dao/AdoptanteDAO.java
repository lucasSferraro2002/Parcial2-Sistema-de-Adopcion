package com.vet.dao;

import com.vet.modelo.Adoptante;
import com.vet.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdoptanteDAO {

    // Crear un nuevo adoptante
    public boolean crear(Adoptante adoptante) {
        String sql = "INSERT INTO adoptantes (nombre, edad, direccion) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, adoptante.getNombre());
            pstmt.setInt(2, adoptante.getEdad());
            pstmt.setString(3, adoptante.getDireccion());

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                // Obtener el ID generado
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        adoptante.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al crear adoptante: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // Buscar adoptante por ID
    public Adoptante buscarPorId(int id) {
        String sql = "SELECT * FROM adoptantes WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Adoptante(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getString("direccion")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar adoptante: " + e.getMessage());
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

    // Obtener todos los adoptantes
    public List<Adoptante> obtenerTodos() {
        List<Adoptante> adoptantes = new ArrayList<>();
        String sql = "SELECT * FROM adoptantes";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Adoptante adoptante = new Adoptante(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getString("direccion")
                );
                adoptantes.add(adoptante);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener adoptantes: " + e.getMessage());
            e.printStackTrace();
        }

        return adoptantes;
    }

    // Actualizar adoptante
    public boolean actualizar(Adoptante adoptante) {
        String sql = "UPDATE adoptantes SET nombre = ?, edad = ?, direccion = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, adoptante.getNombre());
            pstmt.setInt(2, adoptante.getEdad());
            pstmt.setString(3, adoptante.getDireccion());
            pstmt.setInt(4, adoptante.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar adoptante: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // Eliminar adoptante
    public boolean eliminar(int id) {
        String sql = "DELETE FROM adoptantes WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar adoptante: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}