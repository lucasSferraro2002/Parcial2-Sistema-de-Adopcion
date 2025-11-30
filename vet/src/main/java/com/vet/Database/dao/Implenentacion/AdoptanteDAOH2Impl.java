package com.vet.Database.dao.Implenentacion;

import com.vet.Database.dao.DAO;
import com.vet.Database.dao.Interfaces.AdoptanteDAO;
import com.vet.modelo.Adoptante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación H2 del DAO de Adoptante
 */
public class AdoptanteDAOH2Impl extends DAO implements AdoptanteDAO {


    @Override
    public boolean crear(Adoptante adoptante) {
        String sql = "INSERT INTO adoptantes (nombre, edad, direccion) VALUES (?, ?, ?)";

        PreparedStatement pstmt = null;

        try {
            Connection conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, adoptante.getNombre());
            pstmt.setInt(2, adoptante.getEdad());
            pstmt.setString(3, adoptante.getDireccion());

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        adoptante.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            handleSQLException(e, "crear adoptante");
        } finally {
            closeResources(null, pstmt);
        }

        return false;
    }

    @Override
    public Adoptante buscarPorId(int id) {
        String sql = "SELECT * FROM adoptantes WHERE id = ?";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Connection conn = getConnection();
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
            handleSQLException(e, "buscar adoptante por ID");
        } finally {
            closeResources(rs, pstmt);
        }

        return null;
    }

    @Override
    public List<Adoptante> obtenerTodos() {
        List<Adoptante> adoptantes = new ArrayList<>();
        String sql = "SELECT * FROM adoptantes";

        Statement stmt = null;
        ResultSet rs = null;

        try {
            Connection conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

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
            handleSQLException(e, "obtener adoptantes");
        } finally {
            closeResources(rs, stmt);
        }

        return adoptantes;
    }

    @Override
    public boolean actualizar(Adoptante adoptante) {
        String sql = "UPDATE adoptantes SET nombre = ?, edad = ?, direccion = ? WHERE id = ?";

        PreparedStatement pstmt = null;

        try {
            Connection conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, adoptante.getNombre());
            pstmt.setInt(2, adoptante.getEdad());
            pstmt.setString(3, adoptante.getDireccion());
            pstmt.setInt(4, adoptante.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            handleSQLException(e, "actualizar adoptante");
        } finally {
            closeResources(null, pstmt);
        }

        return false;
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM adoptantes WHERE id = ?";

        PreparedStatement pstmt = null;

        try {
            Connection conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            handleSQLException(e, "eliminar adoptante");
        } finally {
            closeResources(null, pstmt);
        }

        return false;
    }
}