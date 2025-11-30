package com.vet.modelo.Mascotas;

import java.util.Date;

public class Conejo extends Mascota {

    private String colorConejo;

    public Conejo(int id, String nombre, Date fechaNacimiento, double peso, String colorConejo) {
        super(id, nombre, peso, fechaNacimiento, "Conejo");
        this.colorConejo = colorConejo;
    }

    // Constructor sin ID
    public Conejo(String nombre, Date fechaNacimiento, double peso, String colorConejo) {
        super(nombre, peso, fechaNacimiento);
        this.colorConejo = colorConejo;
        this.especie = "Conejo";
    }

    public String getColorConejo() {
        return colorConejo;
    }

    public void setColorConejo(String colorConejo) {
        this.colorConejo = colorConejo;
    }

    @Override
    public String getAtributoEspecial() {
        return colorConejo;
    }

    @Override
    public String obtenerCuidados() {
        return "\nCuidados especiales: Tener cuidado, son muy frágiles. Este conejo es color " + colorConejo;
    }

    public String obtenerInfoCompleta() {
        return obtenerInfo() + obtenerCuidados();
    }

    @Override
    public String toString() {
        return "Conejo - " + obtenerInfoCompleta();
    }
}