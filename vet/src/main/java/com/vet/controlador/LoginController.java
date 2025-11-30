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
}