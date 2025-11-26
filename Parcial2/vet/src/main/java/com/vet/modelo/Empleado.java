package com.vet.modelo;

public class Empleado {

    private static Empleado instanciaLogueada;

    // Atributos para la base de datos
    private int id;
    private String nombre;
    private String apellido;
    private String username;
    private String password;

    // Constructor completo
    public Empleado(int id, String nombre, String apellido, String username, String password) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.password = password;
    }

    // Constructor sin ID
    public Empleado(String nombre, String apellido, String username, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.password = password;
    }

    public static void setInstanciaLogueada(Empleado empleado) {
        instanciaLogueada = empleado;
    }

    public static Empleado getInstanciaLogueada() {
        return instanciaLogueada;
    }

    public static void cerrarSesion() {
        instanciaLogueada = null;
    }

    // Verificacion si hay un empleado logueado
    public static boolean hayEmpleadoLogueado() {
        return instanciaLogueada != null;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String obtenerInfo() {
        return "Empleado: " + nombre + " " + apellido;
    }

    public String obtenerNombreCompleto() {
        return nombre + " " + apellido;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}