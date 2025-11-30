package com.vet.controlador;

import com.vet.Database.dao.Implenentacion.AdopcionDAOH2Impl;
import com.vet.Database.dao.Implenentacion.AdoptanteDAOH2Impl;
import com.vet.Database.dao.Interfaces.AdopcionDAO;
import com.vet.Database.dao.Interfaces.AdoptanteDAO;
import com.vet.Database.dao.Interfaces.MascotaDAO;
import com.vet.Database.dao.Implenentacion.MascotaDAOH2Impl;
import com.vet.modelo.*;
import com.vet.modelo.Mascotas.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdopcionController {

    private AdoptanteDAO adoptanteDAO;
    private MascotaDAO mascotaDAO;
    private AdopcionDAO adopcionDAO;
    private SimpleDateFormat formato;

    public AdopcionController() {
        this.adoptanteDAO = new AdoptanteDAOH2Impl();
        this.mascotaDAO = new MascotaDAOH2Impl();
        this.adopcionDAO = new AdopcionDAOH2Impl();
        this.formato = new SimpleDateFormat("dd/MM/yyyy");
    }

    /**
     * Registra una adopción completa
     * 1. Guarda el adoptante en la BD
     * 2. Guarda la mascota en la BD
     * 3. Guarda la adopción en la BD
     * @return La adopción registrada o null si hubo error
     */
    public Adopcion registrarAdopcion(String nombreAdoptante, int edad, String direccion,
                                      String nombreMascota, String especie, Date fechaNacimiento,
                                      double peso, String atributoEspecial) {

        try {
            // Verificar que hay un empleado logueado
            Empleado empleadoLogueado = Empleado.getInstanciaLogueada();

            if (empleadoLogueado == null) {
                System.err.println("ERROR CRÍTICO: No hay empleado logueado");
                return null;
            }

            System.out.println("=== INICIANDO REGISTRO DE ADOPCIÓN ===");
            System.out.println("Empleado: " + empleadoLogueado.obtenerNombreCompleto() + " (ID: " + empleadoLogueado.getId() + ")");

            // 1. Crear y guardar el adoptante
            Adoptante adoptante = new Adoptante(nombreAdoptante, edad, direccion);
            boolean adoptanteGuardado = adoptanteDAO.crear(adoptante);

            if (!adoptanteGuardado) {
                System.err.println("ERROR: No se pudo guardar el adoptante");
                return null;
            }

            System.out.println("✓ Adoptante guardado con ID: " + adoptante.getId());

            // 2. Crear y guardar la mascota
            Mascota mascota = crearMascota(especie, nombreMascota, fechaNacimiento, peso, atributoEspecial);

            if (mascota == null) {
                System.err.println("ERROR: No se pudo crear la mascota");
                return null;
            }

            boolean mascotaGuardada = mascotaDAO.crear(mascota);

            if (!mascotaGuardada) {
                System.err.println("ERROR: No se pudo guardar la mascota");
                return null;
            }

            System.out.println("✓ Mascota guardada con ID: " + mascota.getId());

            // 3. Crear y guardar la adopción
            Adopcion adopcion = new Adopcion(empleadoLogueado, adoptante, mascota);

            System.out.println("Creando adopción con número: " + adopcion.getNumeroAdopcion());
            System.out.println("  - Empleado ID: " + empleadoLogueado.getId());
            System.out.println("  - Adoptante ID: " + adoptante.getId());
            System.out.println("  - Mascota ID: " + mascota.getId());

            boolean adopcionGuardada = adopcionDAO.crear(adopcion);

            if (!adopcionGuardada) {
                System.err.println("ERROR: No se pudo guardar la adopción en la base de datos");
                return null;
            }

            System.out.println("✓ Adopción registrada exitosamente!");
            System.out.println("  - ID de adopción: " + adopcion.getId());
            System.out.println("  - Número de adopción: " + adopcion.getNumeroAdopcion());
            System.out.println("=== FIN REGISTRO ===\n");

            return adopcion;

        } catch (Exception e) {
            System.err.println("ERROR GENERAL al registrar la adopción: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Crea una mascota según la especie
     */
    private Mascota crearMascota(String especie, String nombre, Date fechaNacimiento,
                                 double peso, String atributoEspecial) {
        switch (especie) {
            case "Perro":
                return new Perro(nombre, fechaNacimiento, peso, atributoEspecial);
            case "Gato":
                return new Gato(nombre, fechaNacimiento, peso, atributoEspecial);
            case "Conejo":
                return new Conejo(nombre, fechaNacimiento, peso, atributoEspecial);
            case "Ave":
                return new Ave(nombre, fechaNacimiento, peso, atributoEspecial);
            default:
                System.err.println("Especie desconocida: " + especie);
                return null;
        }
    }

    /**
     * Obtiene las recomendaciones de cuidado según la especie
     */
    public String obtenerRecomendacionesCuidado(String especie) {
        switch (especie) {
            case "Perro":
                return "CUIDADOS PARA PERROS:\n" +
                        "- Paseos diarios de al menos 30 minutos\n" +
                        "- Alimentación balanceada según tamaño\n" +
                        "- Vacunas al día\n" +
                        "- Visitas regulares al veterinario\n" +
                        "- Socialización con otros perros";
            case "Gato":
                return "CUIDADOS PARA GATOS:\n" +
                        "- Bandeja de arena limpia diariamente\n" +
                        "- Alimentación específica para gatos\n" +
                        "- Rascadores para mantener uñas\n" +
                        "- Juguetes para estimulación mental\n" +
                        "- Espacio tranquilo para descansar";
            case "Conejo":
                return "CUIDADOS PARA CONEJOS:\n" +
                        "- Jaula espaciosa y limpia\n" +
                        "- Heno fresco siempre disponible\n" +
                        "- Vegetales frescos diarios\n" +
                        "- Espacio para ejercicio\n" +
                        "- Son animales muy frágiles, manipular con cuidado";
            case "Ave":
                return "CUIDADOS PARA AVES:\n" +
                        "- Jaula limpia y espaciosa\n" +
                        "- Alimento específico para aves\n" +
                        "- Agua fresca diaria\n" +
                        "- Proteger de corrientes de aire\n" +
                        "- Las alas son muy frágiles, tener cuidado";
            default:
                return "";
        }
    }

    // ========== VALIDACIONES ==========

    public boolean validarNombreAdoptante(String nombre) {
        return nombre != null && !nombre.trim().isEmpty() && !nombre.matches(".*\\d.*");
    }

    public boolean validarEdad(String edadStr) {
        try {
            int edad = Integer.parseInt(edadStr);
            return edad >= 18 && edad <= 100;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int parsearEdad(String edadStr) throws NumberFormatException {
        return Integer.parseInt(edadStr);
    }

    public boolean validarDireccion(String direccion) {
        return direccion != null && !direccion.trim().isEmpty();
    }

    public boolean validarNombreMascota(String nombre) {
        return nombre != null && !nombre.trim().isEmpty();
    }

    public Date parsearFecha(String fechaStr) throws ParseException {
        return formato.parse(fechaStr);
    }

    public boolean validarPeso(String pesoStr) {
        try {
            double peso = Double.parseDouble(pesoStr);
            return peso > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public double parsearPeso(String pesoStr) throws NumberFormatException {
        return Double.parseDouble(pesoStr);
    }

    public boolean validarAtributoEspecial(String atributo) {
        return atributo != null && !atributo.trim().isEmpty();
    }

    public boolean hayCamposVaciosAdoptante(String nombre, String edad, String direccion) {
        return nombre == null || nombre.trim().isEmpty() ||
                edad == null || edad.trim().isEmpty() ||
                direccion == null || direccion.trim().isEmpty();
    }

    public boolean hayCamposVaciosMascota(String nombre, String fecha, String peso, String atributo) {
        return nombre == null || nombre.trim().isEmpty() ||
                fecha == null || fecha.trim().isEmpty() ||
                peso == null || peso.trim().isEmpty() ||
                atributo == null || atributo.trim().isEmpty();
    }
}
