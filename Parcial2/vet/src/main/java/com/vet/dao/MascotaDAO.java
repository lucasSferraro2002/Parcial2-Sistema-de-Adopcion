package com.vet.dao;

import com.vet.modelo.Mascotas.*;
import com.vet.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MascotaDAO {

    // Crear una nueva mascota
    public boolean crear(Mascota mascota) {
        String sql = "INSERT INTO mascotas (nombre, especie, fecha_nacimiento, peso, atributo_especial) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, mascota.getNombre());
            pstmt.setString(2, mascota.getEspecie());
            pstmt.setDate(3, new java.sql.Date(mascota.getFechaDeNacimiento().getTime()));
            pstmt.setDouble(4, mascota.getPeso());
            pstmt.setString(5, mascota.getAtributoEspecial());

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                // Obtener el ID generado
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        mascota.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al crear mascota: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // Buscar mascota por ID
    public Mascota buscarPorId(int id) {
        String sql = "SELECT * FROM mascotas WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return crearMascotaDesdeResultSet(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar mascota: " + e.getMessage());
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

    // Obtener todas las mascotas
    public List<Mascota> obtenerTodos() {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM mascotas";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Mascota mascota = crearMascotaDesdeResultSet(rs);
                if (mascota != null) {
                    mascotas.add(mascota);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener mascotas: " + e.getMessage());
            e.printStackTrace();
        }

        return mascotas;
    }

    // Método auxiliar para crear la mascota correcta según la especie
    private Mascota crearMascotaDesdeResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nombre = rs.getString("nombre");
        String especie = rs.getString("especie");
        java.util.Date fechaNacimiento = new java.util.Date(rs.getDate("fecha_nacimiento").getTime());
        double peso = rs.getDouble("peso");
        String atributoEspecial = rs.getString("atributo_especial");

        switch (especie) {
            case "Perro":
                return new Perro(id, nombre, fechaNacimiento, peso, atributoEspecial);
            case "Gato":
                return new Gato(id, nombre, fechaNacimiento, peso, atributoEspecial);
            case "Conejo":
                return new Conejo(id, nombre, fechaNacimiento, peso, atributoEspecial);
            case "Ave":
                return new Ave(id, nombre, fechaNacimiento, peso, atributoEspecial);
            default:
                System.err.println("Especie desconocida: " + especie);
                return null;
        }
    }

    // Actualizar mascota
    public boolean actualizar(Mascota mascota) {
        String sql = "UPDATE mascotas SET nombre = ?, especie = ?, fecha_nacimiento = ?, peso = ?, atributo_especial = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, mascota.getNombre());
            pstmt.setString(2, mascota.getEspecie());
            pstmt.setDate(3, new java.sql.Date(mascota.getFechaDeNacimiento().getTime()));
            pstmt.setDouble(4, mascota.getPeso());
            pstmt.setString(5, mascota.getAtributoEspecial());
            pstmt.setInt(6, mascota.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar mascota: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // Eliminar mascota
    public boolean eliminar(int id) {
        String sql = "DELETE FROM mascotas WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar mascota: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}