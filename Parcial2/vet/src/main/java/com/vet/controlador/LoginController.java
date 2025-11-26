package com.vet.controlador;

import com.vet.modelo.Empleado;
import com.vet.dao.EmpleadoDAO;
import com.vet.modelo.*;


public class LoginController {

    private EmpleadoDAO empleadoDAO;

    public LoginController() {
        this.empleadoDAO = new EmpleadoDAO();
    }

    // Validacion de credenciales

    public Empleado validarCredenciales(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }

        if (password == null || password.trim().isEmpty()) {
            return null;
        }

        Empleado empleado = empleadoDAO.validarLogin(username.trim(), password);

        if (empleado != null) {
            Empleado.setInstanciaLogueada(empleado);
        }

        return empleado;
    }

    // Verifica si hay campos vacíos
    public boolean hayCamposVacios(String username, String password) {
        return username == null || username.trim().isEmpty() ||
               password == null || password.trim().isEmpty();
    }
}