package com.vet.modelo;

import java.util.Date;
import com.vet.modelo.Mascotas.Mascota;
import java.util.UUID;

public class Adopcion {

    private int id;
    private Empleado empleado;
    private Adoptante adoptante;
    private Mascota mascota;
    private Date fechaHoraAdopcion;
    private String numeroAdopcion;

    // Constructor completo
    public Adopcion(int id, Empleado empleado, Adoptante adoptante, Mascota mascota,
                    Date fechaHoraAdopcion, String numeroAdopcion) {
        this.id = id;
        this.empleado = empleado;
        this.adoptante = adoptante;
        this.mascota = mascota;
        this.fechaHoraAdopcion = fechaHoraAdopcion;
        this.numeroAdopcion = numeroAdopcion;
    }

    // Constructor sin ID
    public Adopcion(Empleado empleado, Adoptante adoptante, Mascota mascota) {
        this.empleado = empleado;
        this.adoptante = adoptante;
        this.mascota = mascota;
        this.fechaHoraAdopcion = new Date();
        this.numeroAdopcion = generarNumeroAdopcion();
    }

    private String generarNumeroAdopcion() {
        // Genera un número único
        long timestamp = System.currentTimeMillis();
        String uniquePart = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "ADO-" + timestamp + "-" + uniquePart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public Adoptante getAdoptante() {
        return adoptante;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public Date getFechaHoraAdopcion() {
        return fechaHoraAdopcion;
    }

    public String getNumeroAdopcion() {
        return numeroAdopcion;
    }

    public void setNumeroAdopcion(String numeroAdopcion) {
        this.numeroAdopcion = numeroAdopcion;
    }
}