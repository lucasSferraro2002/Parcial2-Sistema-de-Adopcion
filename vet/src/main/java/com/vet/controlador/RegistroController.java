package com.vet.controlador;
import com.vet.Database.dao.Implenentacion.EmpleadoDAOH2Impl;
import com.vet.modelo.Empleado;
import com.vet.Database.dao.Interfaces.EmpleadoDAO;


public class RegistroController {

    private EmpleadoDAO empleadoDAO;

    public RegistroController() {
        this.empleadoDAO = new EmpleadoDAOH2Impl();
    }

    // Registra un nuevo empleado

    public boolean registrarEmpleado(String nombre, String apellido, String username, String password) {

        Empleado nuevoEmpleado = new Empleado(nombre, apellido, username, password);

        return empleadoDAO.crear(nuevoEmpleado);
    }

    // Valida que los campos esten correctos

    public boolean hayCamposVacios(String nombre, String apellido, String username,
                                   String password, String confirmarPassword) {
        return nombre == null || nombre.trim().isEmpty() ||
                apellido == null || apellido.trim().isEmpty() ||
                username == null || username.trim().isEmpty() ||
                password == null || password.isEmpty() ||
                confirmarPassword == null || confirmarPassword.isEmpty();
    }

    public boolean nombreContieneNumeros(String nombre, String apellido) {
        return nombre.matches(".*\\d.*") || apellido.matches(".*\\d.*");
    }

    public boolean usernameValido(String username) {
        return username != null && username.length() >= 4;
    }

    public boolean passwordValido(String password) {
        return password != null && password.length() >= 6;
    }

    public boolean passwordsCoinciden(String password, String confirmarPassword) {
        return password != null && password.equals(confirmarPassword);
    }

    public boolean usernameExiste(String username) {
        return empleadoDAO.existeUsername(username);
    }
}