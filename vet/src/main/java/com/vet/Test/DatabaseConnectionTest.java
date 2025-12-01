package com.vet.Test;

import com.vet.Database.DatabaseConnection;

import java.sql.Connection;
public class DatabaseConnectionTest {

    public static void main(String[] args) {

        System.out.println("   TEST Conexión a Base de Datos");

        testConexion();
        testTablas();

        System.out.println("   TEST COMPLETADO");

    }

    private static void testConexion() {
        System.out.println("TEST: Verificar Conexión");

        try {
            Connection conn = DatabaseConnection.getConnection();

            if (conn != null && !conn.isClosed()) {
                System.out.println("  PASÓ: Conexión establecida correctamente");
                System.out.println("  Base de datos: H2");
                System.out.println("  Estado: Activa");
            } else {
                System.out.println(" Conexión cerrada o nula");
            }

        } catch (Exception e) {
            System.out.println("  Error al conectar");
            e.printStackTrace();
        }
        System.out.println();
    }

    private static void testTablas() {
        System.out.println("TEST: Verificar Tablas Creadas");

        try {
            Connection conn = DatabaseConnection.getConnection();
            var metaData = conn.getMetaData();

            String[] tablas = {"EMPLEADOS", "ADOPTANTES", "MASCOTAS", "ADOPCIONES"};
            int tablasEncontradas = 0;

            for (String tabla : tablas) {
                var rs = metaData.getTables(null, null, tabla, new String[]{"TABLE"});
                if (rs.next()) {
                    System.out.println("  Tabla '" + tabla + "' existe");
                    tablasEncontradas++;
                } else {
                    System.out.println("  Tabla '" + tabla + "' NO existe");
                }
                rs.close();
            }

            if (tablasEncontradas == tablas.length) {
                System.out.println("\n PASÓ: Todas las tablas existen");
            } else {
                System.out.println("\n Faltan " + (tablas.length - tablasEncontradas) + " tabla(s)");
            }

        } catch (Exception e) {
            System.out.println(" Error al verificar tablas");
        }
    }
}