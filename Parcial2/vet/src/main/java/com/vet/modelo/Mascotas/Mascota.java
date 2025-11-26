package com.vet.modelo.Mascotas;

import java.util.Date;

public abstract class Mascota {

    protected int id;
    protected String nombre;
    protected double peso;
    protected Date fechaDeNacimiento;
    protected String especie;  // "Perro", "Gato", "Conejo", "Ave"


    public Mascota(int id, String nombre, double peso, Date fechaNacimiento, String especie) {
        this.id = id;
        this.nombre = nombre;
        this.peso = peso;
        this.fechaDeNacimiento = fechaNacimiento;
        this.especie = especie;
    }

    // Constructor sin ID
    public Mascota(String nombre, double peso, Date fechaNacimiento) {
        this.nombre = nombre;
        this.peso = peso;
        this.fechaDeNacimiento = fechaNacimiento;
        this.especie = this.getClass().getSimpleName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Date fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    // Metodos abstractos
    public abstract String obtenerCuidados();
    public abstract String getAtributoEspecial();

    public String obtenerInfo() {
        return "Su nombre es " + nombre + ", nació el " + fechaDeNacimiento + " y pesa " + peso + "kg";
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " - " + obtenerInfo();
    }
}