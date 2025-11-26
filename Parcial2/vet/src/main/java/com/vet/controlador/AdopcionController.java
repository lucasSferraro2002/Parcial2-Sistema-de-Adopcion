package com.vet.controlador;

import com.vet.modelo.*;
import com.vet.modelo.Mascotas.*;
import com.vet.dao.*;
import com.vet.modelo.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdopcionController {

    private AdoptanteDAO adoptanteDAO;
    private MascotaDAO mascotaDAO;
    private AdopcionDAO adopcionDAO;
    private SimpleDateFormat formato;

    public AdopcionController() {
        this.adoptanteDAO = new AdoptanteDAO();
        this.mascotaDAO = new MascotaDAO();
        this.adopcionDAO = new AdopcionDAO();
        this.formato = new SimpleDateFormat("dd/MM/yyyy");
    }

    // Registra una adopción completa

    public Adopcion registrarAdopcion(String nombreAdoptante, int edad, String direccion,
                                      String nombreMascota, String especie, Date fechaNacimiento,
                                      double peso, String atributoEspecial) {

        Adoptante adoptante = new Adoptante(nombreAdoptante, edad, direccion);
        if (!adoptanteDAO.crear(adoptante)) {
            return null;
        }

        Mascota mascota = crearMascota(especie, nombreMascota, fechaNacimiento, peso, atributoEspecial);
        if (mascota == null || !mascotaDAO.crear(mascota)) {
            return null;
        }

        Empleado empleadoLogueado = Empleado.getInstanciaLogueada();
        Adopcion adopcion = new Adopcion(empleadoLogueado, adoptante, mascota);

        if (adopcionDAO.crear(adopcion)) {
            return adopcion;
        }

        return null;
    }

    // Crea una mascota según la especie

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
                return null;
        }
    }

    // Obtiene las recomendaciones de cuidado según la especie

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

    // Validaciones

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