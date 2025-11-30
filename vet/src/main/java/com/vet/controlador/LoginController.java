package com.vet.controlador;

import com.vet.Database.dao.Implenentacion.EmpleadoDAOH2Impl;
import com.vet.modelo.Empleado;
import com.vet.Database.dao.Interfaces.EmpleadoDAO;


public class LoginController {

    private EmpleadoDAO empleadoDAO;

    public LoginController() {
        this.empleadoDAO = new EmpleadoDAOH2Impl();
    }

    // Verifica si hay campos vacíos
    public boolean hayCamposVacios(String username, String password) {
        return username == null || username.trim().isEmpty() ||
               password == null || password.trim().isEmpty();
    }

    public Empleado validarCredenciales(String username, String password) {
        // Validaciones básicas
        if (username == null || username.trim().isEmpty()) {
            return null;
        }

        if (password == null || password.trim().isEmpty()) {
            return null;
        }

        // Validar contra la base de datos
        Empleado empleado = empleadoDAO.validarLogin(username.trim(), password);

        if (empleado != null) {
            // Establecer como empleado logueado (Singleton)
            Empleado.setInstanciaLogueada(empleado);
        }

        return empleado;
    }
}