package com.vet.Database.dao.Implenentacion;

import com.vet.Database.dao.DAO;
import com.vet.Database.dao.Interfaces.MascotaDAO;
import com.vet.modelo.Mascotas.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MascotaDAOH2Impl extends DAO implements MascotaDAO {

    @Override
    public boolean crear(Mascota mascota) {
        String sql = "INSERT INTO mascotas (nombre, especie, fecha_nacimiento, peso, atributo_especial) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement pstmt = null;

        try {
            Connection conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, mascota.getNombre());
            pstmt.setString(2, mascota.getEspecie());
            pstmt.setDate(3, new java.sql.Date(mascota.getFechaDeNacimiento().getTime()));
            pstmt.setDouble(4, mascota.getPeso());
            pstmt.setString(5, mascota.getAtributoEspecial());

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        mascota.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            handleSQLException(e, "crear mascota");
        } finally {
            closeResources(null, pstmt);
        }

        return false;
    }

    @Override
    public Mascota buscarPorId(int id) {
        String sql = "SELECT * FROM mascotas WHERE id = ?";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Connection conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return crearMascotaDesdeResultSet(rs);
            }

        } catch (SQLException e) {
            handleSQLException(e, "buscar mascota por ID");
        } finally {
            closeResources(rs, pstmt);
        }

        return null;
    }

    @Override
    public List<Mascota> obtenerTodos() {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM mascotas";

        Statement stmt = null;
        ResultSet rs = null;

        try {
            Connection conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Mascota mascota = crearMascotaDesdeResultSet(rs);
                if (mascota != null) {
                    mascotas.add(mascota);
                }
            }

        } catch (SQLException e) {
            handleSQLException(e, "obtener mascotas");
        } finally {
            closeResources(rs, stmt);
        }

        return mascotas;
    }

    @Override
    public boolean actualizar(Mascota mascota) {
        String sql = "UPDATE mascotas SET nombre = ?, especie = ?, fecha_nacimiento = ?, peso = ?, atributo_especial = ? WHERE id = ?";

        PreparedStatement pstmt = null;

        try {
            Connection conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, mascota.getNombre());
            pstmt.setString(2, mascota.getEspecie());
            pstmt.setDate(3, new java.sql.Date(mascota.getFechaDeNacimiento().getTime()));
            pstmt.setDouble(4, mascota.getPeso());
            pstmt.setString(5, mascota.getAtributoEspecial());
            pstmt.setInt(6, mascota.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            handleSQLException(e, "actualizar mascota");
        } finally {
            closeResources(null, pstmt);
        }

        return false;
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM mascotas WHERE id = ?";

        PreparedStatement pstmt = null;

        try {
            Connection conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            handleSQLException(e, "eliminar mascota");
        } finally {
            closeResources(null, pstmt);
        }

        return false;
    }

    /**
     * Método auxiliar para crear la mascota correcta según la especie
     */
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
}