package com.vet.Database.dao.Interfaces;

import com.vet.modelo.Adopcion;


public interface AdopcionDAO {

    boolean crear(Adopcion adopcion);
    Adopcion buscarPorId(int id);
    Adopcion buscarPorNumero(String numeroAdopcion);
    java.util.List<Adopcion> obtenerTodos();
    java.util.List<Adopcion> obtenerPorEmpleado(int empleadoId);
    boolean eliminar(int id);
}