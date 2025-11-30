package com.vet.modelo.Mascotas;

import java.util.Date;

public class Perro extends Mascota {

    private String tamañoPerro;

    public Perro(int id, String nombre, Date fechaNacimiento, double peso, String tamañoPerro) {
        super(id, nombre, peso, fechaNacimiento, "Perro");
        this.tamañoPerro = tamañoPerro;
    }

    // Constructor sin ID
    public Perro(String nombre, Date fechaNacimiento, double peso, String tamañoPerro) {
        super(nombre, peso, fechaNacimiento);
        this.tamañoPerro = tamañoPerro;
        this.especie = "Perro";
    }

    public String getTamañoPerro() {
        return tamañoPerro;
    }

    public void setTamañoPerro(String tamañoPerro) {
        this.tamañoPerro = tamañoPerro;
    }

    @Override
    public String getAtributoEspecial() {
        return tamañoPerro;
    }

    @Override
    public String obtenerCuidados() {
        return "\nCuidados especiales: Manejar con cuidado según el tamaño. Este perro es de tamaño " + tamañoPerro;
    }

    public String obtenerInfoCompleta() {
        return obtenerInfo() + obtenerCuidados();
    }

    @Override
    public String toString() {
        return "Perro - " + obtenerInfoCompleta();
    }
}