package com.vet.modelo.Mascotas;

import java.util.Date;

public class Gato extends Mascota {

    private String temperamentoGato;

    public Gato(int id, String nombre, Date fechaNacimiento, double peso, String temperamentoGato) {
        super(id, nombre, peso, fechaNacimiento, "Gato");
        this.temperamentoGato = temperamentoGato;
    }

    // Constructor sin el ID
    public Gato(String nombre, Date fechaNacimiento, double peso, String temperamentoGato) {
        super(nombre, peso, fechaNacimiento);
        this.temperamentoGato = temperamentoGato;
        this.especie = "Gato";
    }

    public String getTemperamentoGato() {
        return temperamentoGato;
    }

    public void setTemperamentoGato(String temperamentoGato) {
        this.temperamentoGato = temperamentoGato;
    }

    @Override
    public String getAtributoEspecial() {
        return temperamentoGato;
    }

    @Override
    public String obtenerCuidados() {
        return "\nCuidado con el temperamento del gato, algunos son agresivos. Este es " + temperamentoGato;
    }

    public String obtenerInfoCompleta() {
        return obtenerInfo() + obtenerCuidados();
    }

    @Override
    public String toString() {
        return "Gato - " + obtenerInfoCompleta();
    }
}