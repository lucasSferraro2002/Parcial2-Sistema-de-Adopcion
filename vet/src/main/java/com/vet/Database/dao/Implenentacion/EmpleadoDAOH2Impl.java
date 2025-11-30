package com.vet.Database.dao.Implenentacion;

import com.vet.Database.dao.DAO;
import com.vet.Database.dao.Interfaces.EmpleadoDAO;
import com.vet.modelo.Empleado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class EmpleadoDAOH2Impl extends DAO implements EmpleadoDAO {

    @Override
    public boolean crear(Empleado empleado) {
        String sql = "INSERT INTO empleados (nombre, apellido, username, password) VALUES (?, ?, ?, ?)";

        PreparedStatement pstmt = null;

        try {
            Connection conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, empleado.getNombre());
            pstmt.setString(2, empleado.getApellido());
            pstmt.setString(3, empleado.getUsername());
            pstmt.setString(4, empleado.getPassword());

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        empleado.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            handleSQLException(e, "crear empleado");
        } finally {
            closeResources(null, pstmt);
        }

        return false;
    }

    @Override
    public Empleado buscarPorId(int id) {
        String sql = "SELECT * FROM empleados WHERE id = ?";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Connection conn = getConnection();
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
            handleSQLException(e, "buscar empleado por ID");
        } finally {
            closeResources(rs, pstmt);
        }

        return null;
    }

    @Override
    public Empleado buscarPorUsername(String username) {
        String sql = "SELECT * FROM empleados WHERE username = ?";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Connection conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
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
            handleSQLException(e, "buscar empleado por username");
        } finally {
            closeResources(rs, pstmt);
        }

        return null;
    }

    @Override
    public Empleado validarLogin(String username, String password) {
        Empleado empleado = buscarPorUsername(username);

        if (empleado != null && empleado.getPassword().equals(password)) {
            return empleado;
        }

        return null;
    }

    @Override
    public boolean existeUsername(String username) {
        return buscarPorUsername(username) != null;
    }

    @Override
    public List<Empleado> obtenerTodos() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleados";

        Statement stmt = null;
        ResultSet rs = null;

        try {
            Connection conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

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
            handleSQLException(e, "obtener empleados");
        } finally {
            closeResources(rs, stmt);
        }

        return empleados;
    }

    @Override
    public boolean actualizar(Empleado empleado) {
        String sql = "UPDATE empleados SET nombre = ?, apellido = ?, username = ?, password = ? WHERE id = ?";

        PreparedStatement pstmt = null;

        try {
            Connection conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, empleado.getNombre());
            pstmt.setString(2, empleado.getApellido());
            pstmt.setString(3, empleado.getUsername());
            pstmt.setString(4, empleado.getPassword());
            pstmt.setInt(5, empleado.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            handleSQLException(e, "actualizar empleado");
        } finally {
            closeResources(null, pstmt);
        }

        return false;
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM empleados WHERE id = ?";

        PreparedStatement pstmt = null;

        try {
            Connection conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            handleSQLException(e, "eliminar empleado");
        } finally {
            closeResources(null, pstmt);
        }

        return false;
    }
}