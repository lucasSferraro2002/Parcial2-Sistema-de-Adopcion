package com.vet.Database.dao.Implenentacion;

import com.vet.Database.dao.DAO;
import com.vet.Database.dao.Interfaces.AdopcionDAO;
import com.vet.Database.dao.Interfaces.AdoptanteDAO;
import com.vet.Database.dao.Interfaces.EmpleadoDAO;
import com.vet.Database.dao.Interfaces.MascotaDAO;
import com.vet.modelo.Adopcion;
import com.vet.modelo.Adoptante;
import com.vet.modelo.Empleado;
import com.vet.modelo.Mascotas.Mascota;

import java.sql.Statement;;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AdopcionDAOH2Impl extends DAO implements AdopcionDAO {

    private EmpleadoDAO empleadoDAO;
    private AdoptanteDAO adoptanteDAO;
    private MascotaDAO mascotaDAO;

    public AdopcionDAOH2Impl() {
        this.empleadoDAO = new EmpleadoDAOH2Impl();
        this.adoptanteDAO = new AdoptanteDAOH2Impl();
        this.mascotaDAO = new MascotaDAOH2Impl();
    }

    @Override
    public boolean crear(Adopcion adopcion) {
        String sql = "INSERT INTO adopciones (numero_adopcion, empleado_id, adoptante_id, mascota_id, fecha_hora) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement pstmt = null;

        try {
            Connection conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, adopcion.getNumeroAdopcion());
            pstmt.setInt(2, adopcion.getEmpleado().getId());
            pstmt.setInt(3, adopcion.getAdoptante().getId());
            pstmt.setInt(4, adopcion.getMascota().getId());
            pstmt.setTimestamp(5, new Timestamp(adopcion.getFechaHoraAdopcion().getTime()));

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        adopcion.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            handleSQLException(e, "crear adopción");
        } finally {
            closeResources(null, pstmt);
        }

        return false;
    }

    @Override
    public Adopcion buscarPorId(int id) {
        String sql = "SELECT * FROM adopciones WHERE id = ?";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Connection conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return crearAdopcionDesdeResultSet(rs);
            }

        } catch (SQLException e) {
            handleSQLException(e, "buscar adopción por ID");
        } finally {
            closeResources(rs, pstmt);
        }

        return null;
    }

    @Override
    public Adopcion buscarPorNumero(String numeroAdopcion) {
        String sql = "SELECT * FROM adopciones WHERE numero_adopcion = ?";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Connection conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, numeroAdopcion);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return crearAdopcionDesdeResultSet(rs);
            }

        } catch (SQLException e) {
            handleSQLException(e, "buscar adopción por número");
        } finally {
            closeResources(rs, pstmt);
        }

        return null;
    }

    @Override
    public List<Adopcion> obtenerTodos() {
        List<Adopcion> adopciones = new ArrayList<>();
        String sql = "SELECT * FROM adopciones ORDER BY fecha_hora DESC";

        Statement stmt = null;
        ResultSet rs = null;

        try {
            Connection conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Adopcion adopcion = crearAdopcionDesdeResultSet(rs);
                if (adopcion != null) {
                    adopciones.add(adopcion);
                }
            }

        } catch (SQLException e) {
            handleSQLException(e, "obtener adopciones");
        } finally {
            closeResources(rs, stmt);
        }

        return adopciones;
    }

    @Override
    public List<Adopcion> obtenerPorEmpleado(int empleadoId) {
        List<Adopcion> adopciones = new ArrayList<>();
        String sql = "SELECT * FROM adopciones WHERE empleado_id = ? ORDER BY fecha_hora DESC";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Connection conn = getConnection();
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
            handleSQLException(e, "obtener adopciones por empleado");
        } finally {
            closeResources(rs, pstmt);
        }

        return adopciones;
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM adopciones WHERE id = ?";

        PreparedStatement pstmt = null;

        try {
            Connection conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            handleSQLException(e, "eliminar adopción");
        } finally {
            closeResources(null, pstmt);
        }

        return false;
    }

    /**
     * Método auxiliar para crear la adopción desde ResultSet
     */
    private Adopcion crearAdopcionDesdeResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String numeroAdopcion = rs.getString("numero_adopcion");
        int empleadoId = rs.getInt("empleado_id");
        int adoptanteId = rs.getInt("adoptante_id");
        int mascotaId = rs.getInt("mascota_id");
        Timestamp fechaHora = rs.getTimestamp("fecha_hora");

        // Obtener las entidades relacionadas
        Empleado empleado = empleadoDAO.buscarPorId(empleadoId);
        Adoptante adoptante = adoptanteDAO.buscarPorId(adoptanteId);
        Mascota mascota = mascotaDAO.buscarPorId(mascotaId);

        if (empleado != null && adoptante != null && mascota != null) {
            return new Adopcion(id, empleado, adoptante, mascota,
                    new java.util.Date(fechaHora.getTime()), numeroAdopcion);
        }

        return null;
    }
}