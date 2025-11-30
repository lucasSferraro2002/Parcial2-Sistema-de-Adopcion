package com.vet.modelo.Mascotas;

import java.util.Date;

public class Ave extends Mascota {

    private String tipoAve;

    public Ave(int id, String nombre, Date fechaNacimiento, double peso, String tipoAve) {
        super(id, nombre, peso, fechaNacimiento, "Ave");
        this.tipoAve = tipoAve;
    }

    // Constructor sin ID
    public Ave(String nombre, Date fechaNacimiento, double peso, String tipoAve) {
        super(nombre, peso, fechaNacimiento);
        this.tipoAve = tipoAve;
        this.especie = "Ave";
    }

    public String getTipoAve() {
        return tipoAve;
    }

    public void setTipoAve(String tipoAve) {
        this.tipoAve = tipoAve;
    }

    @Override
    public String getAtributoEspecial() {
        return tipoAve;
    }

    @Override
    public String obtenerCuidados() {
        return "\nCuidados especiales: Tener cuidado con las alas, son frágiles";
    }

    public String obtenerInfoCompleta() {
        return obtenerInfo() + obtenerCuidados();
    }

    @Override
    public String toString() {
        return "Ave - " + obtenerInfoCompleta();
    }
}