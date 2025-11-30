package com.vet.controlador;

import com.vet.Database.dao.Implenentacion.AdopcionDAOH2Impl;
import com.vet.modelo.Adopcion;
import com.vet.modelo.Empleado;
import com.vet.Database.dao.Interfaces.AdopcionDAO;

import java.util.List;

/**
 * Controlador para el Historial de Adopciones
 * Maneja la lógica de consulta de adopciones entre la Vista y el Modelo
 */
public class HistorialController {

    private AdopcionDAO adopcionDAO;

    public HistorialController() {
        this.adopcionDAO = new AdopcionDAOH2Impl();
    }

    // Obtiene todas las adopciones del empleado logueado

    public List<Adopcion> obtenerAdopcionesEmpleadoLogueado() {
        Empleado empleadoLogueado = Empleado.getInstanciaLogueada();
        return adopcionDAO.obtenerPorEmpleado(empleadoLogueado.getId());
    }

    // Obtiene todas las adopciones del sistema

    public List<Adopcion> obtenerTodasLasAdopciones() {
        return adopcionDAO.obtenerTodos();
    }

    //Busca una adopción segun el número

    public Adopcion buscarPorNumero(String numeroAdopcion) {
        return adopcionDAO.buscarPorNumero(numeroAdopcion);
    }

    // Verifica si hay adopciones registradas

    public boolean hayAdopciones() {
        Empleado empleadoLogueado = Empleado.getInstanciaLogueada();
        List<Adopcion> adopciones = adopcionDAO.obtenerPorEmpleado(empleadoLogueado.getId());
        return !adopciones.isEmpty();
    }
}