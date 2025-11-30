package com.vet.Database.dao;

import com.vet.Database.DatabaseConnection;

import java.sql.*;


public abstract class DAO {

    protected Connection getConnection() {
        return DatabaseConnection.getConnection();
    }

    protected void closeResources(ResultSet rs, Statement stmt) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar recursos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    protected void handleSQLException(SQLException e, String operation) {
        System.err.println("Error en " + operation + ": " + e.getMessage());
        e.printStackTrace();
    }
}