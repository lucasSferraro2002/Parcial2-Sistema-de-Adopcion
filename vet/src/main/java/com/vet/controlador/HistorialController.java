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

    /**
     * Obtiene todas las adopciones del empleado logueado
     */
    public List<Adopcion> obtenerAdopcionesEmpleadoLogueado() {
        Empleado empleadoLogueado = Empleado.getInstanciaLogueada();

        if (empleadoLogueado == null) {
            System.err.println("ERROR: No hay empleado logueado al consultar historial");
            return new java.util.ArrayList<>();
        }

        System.out.println("\n=== CONSULTANDO HISTORIAL ===");
        System.out.println("Empleado logueado: " + empleadoLogueado.obtenerNombreCompleto());
        System.out.println("ID del empleado: " + empleadoLogueado.getId());

        List<Adopcion> adopciones = adopcionDAO.obtenerPorEmpleado(empleadoLogueado.getId());

        System.out.println("Adopciones encontradas: " + adopciones.size());

        if (adopciones.isEmpty()) {
            System.out.println("⚠ No se encontraron adopciones para este empleado");

            // Verificar si hay adopciones en general
            List<Adopcion> todasAdopciones = adopcionDAO.obtenerTodos();
            System.out.println("Total de adopciones en el sistema: " + todasAdopciones.size());

            if (!todasAdopciones.isEmpty()) {
                System.out.println("Las adopciones existentes son de los siguientes empleados:");
                for (Adopcion a : todasAdopciones) {
                    System.out.println("  - Adopción " + a.getNumeroAdopcion() +
                            " -> Empleado ID: " + a.getEmpleado().getId() +
                            " (" + a.getEmpleado().obtenerNombreCompleto() + ")");
                }
            }
        } else {
            System.out.println("✓ Adopciones cargadas correctamente:");
            for (Adopcion a : adopciones) {
                System.out.println("  - " + a.getNumeroAdopcion() +
                        " | " + a.getMascota().getNombre() +
                        " | " + a.getAdoptante().getNombre());
            }
        }

        System.out.println("=== FIN CONSULTA ===\n");

        return adopciones;
    }

    /**
     * Obtiene todas las adopciones del sistema
     */
    public List<Adopcion> obtenerTodasLasAdopciones() {
        return adopcionDAO.obtenerTodos();
    }

    /**
     * Busca una adopción según el número
     */
    public Adopcion buscarPorNumero(String numeroAdopcion) {
        System.out.println("Buscando adopción: " + numeroAdopcion);
        Adopcion adopcion = adopcionDAO.buscarPorNumero(numeroAdopcion);

        if (adopcion != null) {
            System.out.println("✓ Adopción encontrada");
        } else {
            System.out.println("⚠ Adopción no encontrada");
        }

        return adopcion;
    }

    /**
     * Verifica si hay adopciones registradas
     */
    public boolean hayAdopciones() {
        Empleado empleadoLogueado = Empleado.getInstanciaLogueada();

        if (empleadoLogueado == null) {
            return false;
        }

        List<Adopcion> adopciones = adopcionDAO.obtenerPorEmpleado(empleadoLogueado.getId());
        return !adopciones.isEmpty();
    }
}