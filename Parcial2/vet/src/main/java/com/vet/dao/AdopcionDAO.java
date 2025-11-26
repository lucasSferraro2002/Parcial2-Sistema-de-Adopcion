package com.vet.dao;

import com.vet.modelo.Adopcion;
import com.vet.modelo.Adoptante;
import com.vet.modelo.Empleado;
import com.vet.modelo.Mascotas.Mascota;
import com.vet.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdopcionDAO {

    private EmpleadoDAO empleadoDAO;
    private AdoptanteDAO adoptanteDAO;
    private MascotaDAO mascotaDAO;

    public AdopcionDAO() {
        this.empleadoDAO = new EmpleadoDAO();
        this.adoptanteDAO = new AdoptanteDAO();
        this.mascotaDAO = new MascotaDAO();
    }

    // Crear una nueva adopción
    public boolean crear(Adopcion adopcion) {
        String sql = "INSERT INTO adopciones (numero_adopcion, empleado_id, adoptante_id, mascota_id, fecha_hora) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, adopcion.getNumeroAdopcion());
            pstmt.setInt(2, adopcion.getEmpleado().getId());
            pstmt.setInt(3, adopcion.getAdoptante().getId());
            pstmt.setInt(4, adopcion.getMascota().getId());
            pstmt.setTimestamp(5, new Timestamp(adopcion.getFechaHoraAdopcion().getTime()));

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                // Obtener el ID generado
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        adopcion.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al crear adopción: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // Buscar adopción por ID
    public Adopcion buscarPorId(int id) {
        String sql = "SELECT * FROM adopciones WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return crearAdopcionDesdeResultSet(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar adopción: " + e.getMessage());
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

    // Buscar adopción por número
    public Adopcion buscarPorNumero(String numeroAdopcion) {
        String sql = "SELECT * FROM adopciones WHERE numero_adopcion = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, numeroAdopcion);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return crearAdopcionDesdeResultSet(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar adopción: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    // Obtener todas las adopciones
    public List<Adopcion> obtenerTodos() {
        List<Adopcion> adopciones = new ArrayList<>();
        String sql = "SELECT * FROM adopciones ORDER BY fecha_hora DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Adopcion adopcion = crearAdopcionDesdeResultSet(rs);
                if (adopcion != null) {
                    adopciones.add(adopcion);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener adopciones: " + e.getMessage());
            e.printStackTrace();
        }

        return adopciones;
    }

    // Obtener adopciones por empleado
    public List<Adopcion> obtenerPorEmpleado(int empleadoId) {
        List<Adopcion> adopciones = new ArrayList<>();
        String sql = "SELECT * FROM adopciones WHERE empleado_id = ? ORDER BY fecha_hora DESC";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, empleadoId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Adopcion adopcion = crearAdopcionDesdeResultSet(rs);
                if (adopcion != null) {
                    adopciones.add(adopcion);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener adopciones por empleado: " + e.getMessage());
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

        return adopciones;
    }

    private Adopcion crearAdopcionDesdeResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String numeroAdopcion = rs.getString("numero_adopcion");
        int empleadoId = rs.getInt("empleado_id");
        int adoptanteId = rs.getInt("adoptante_id");
        int mascotaId = rs.getInt("mascota_id");
        Timestamp fechaHora = rs.getTimestamp("fecha_hora");

        // IMPORTANTE: No cerrar la conexión aquí, se cerrará en el método que llamó
        // Obtener las entidades relacionadas usando la misma conexión
        Empleado empleado = empleadoDAO.buscarPorId(empleadoId);
        Adoptante adoptante = adoptanteDAO.buscarPorId(adoptanteId);
        Mascota mascota = mascotaDAO.buscarPorId(mascotaId);

        if (empleado != null && adoptante != null && mascota != null) {
            return new Adopcion(id, empleado, adoptante, mascota,
                    new java.util.Date(fechaHora.getTime()), numeroAdopcion);
        }

        return null;
    }

    // Eliminar adopción
    public boolean eliminar(int id) {
        String sql = "DELETE FROM adopciones WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar adopción: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}