package com.vet.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String URL = "jdbc:h2:./veterinaria";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private static Connection connection = null;
    private static boolean tablasInicializadas = false;

    // Obtener la conexión
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("org.h2.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);

                if (!tablasInicializadas) {
                    inicializarTablas();
                    tablasInicializadas = true;
                }
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver de H2");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos");
            e.printStackTrace();
        }
        return connection;
    }

    // Crear las tablas si no existen
    private static void inicializarTablas() {
        try (Statement stmt = connection.createStatement()) {

            // Tabla de Empleados
            String createEmpleadosTable = """
                CREATE TABLE IF NOT EXISTS empleados (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nombre VARCHAR(100) NOT NULL,
                    apellido VARCHAR(100) NOT NULL,
                    username VARCHAR(50) UNIQUE NOT NULL,
                    password VARCHAR(255) NOT NULL
                )
            """;
            stmt.execute(createEmpleadosTable);

            // Tabla de Adoptantes
            String createAdoptantesTable = """
                CREATE TABLE IF NOT EXISTS adoptantes (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nombre VARCHAR(200) NOT NULL,
                    edad INT NOT NULL,
                    direccion VARCHAR(300) NOT NULL
                )
            """;
            stmt.execute(createAdoptantesTable);

            // Tabla de Mascotas
            String createMascotasTable = """
                CREATE TABLE IF NOT EXISTS mascotas (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nombre VARCHAR(100) NOT NULL,
                    especie VARCHAR(50) NOT NULL,
                    fecha_nacimiento DATE NOT NULL,
                    peso DOUBLE NOT NULL,
                    atributo_especial VARCHAR(100)
                )
            """;
            stmt.execute(createMascotasTable);

            // Tabla de Adopciones
            String createAdopcionesTable = """
                CREATE TABLE IF NOT EXISTS adopciones (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    numero_adopcion VARCHAR(50) UNIQUE NOT NULL,
                    empleado_id INT NOT NULL,
                    adoptante_id INT NOT NULL,
                    mascota_id INT NOT NULL,
                    fecha_hora TIMESTAMP NOT NULL,
                    FOREIGN KEY (empleado_id) REFERENCES empleados(id),
                    FOREIGN KEY (adoptante_id) REFERENCES adoptantes(id),
                    FOREIGN KEY (mascota_id) REFERENCES mascotas(id)
                )
            """;
            stmt.execute(createAdopcionesTable);

            System.out.println("Tablas inicializadas correctamente");

        } catch (SQLException e) {
            System.err.println("Error al crear las tablas");
            e.printStackTrace();
        }
    }

    // Cerrar la conexión
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión cerrada");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión");
            e.printStackTrace();
        }
    }

}