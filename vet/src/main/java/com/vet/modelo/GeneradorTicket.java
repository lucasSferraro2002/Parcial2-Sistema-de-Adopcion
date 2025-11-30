package com.vet.modelo;

public class GeneradorTicket {

    public static String generarTicket(Adopcion adopcion) {
        return "=========================================\n" +
                "       TICKET DE ADOPCIÓN\n" +
                "=========================================\n" +
                "Número de Adopción: " + adopcion.getNumeroAdopcion() + "\n" +
                "Fecha y Hora: " + adopcion.getFechaHoraAdopcion() + "\n\n" +
                "========== DATOS DEL EMPLEADO ==========\n" +
                adopcion.getEmpleado().obtenerInfo() + "\n\n" +
                "========== DATOS DEL ADOPTANTE =========\n" +
                adopcion.getAdoptante().obtenerInfo() + "\n\n" +
                "========== DATOS DE LA MASCOTA =========\n" +
                adopcion.getMascota().toString() + "\n" +
                "=========================================\n" +
                "   ¡Gracias por adoptar con nosotros!\n" +
                "=========================================\n";
    }

    public static void imprimirTicket(Adopcion adopcion) {
        System.out.println(generarTicket(adopcion));
    }
}