package com.vet.Database.dao.Interfaces;

import com.vet.modelo.Empleado;


public interface EmpleadoDAO {

    boolean crear(Empleado empleado);
    Empleado buscarPorId(int id);
    Empleado buscarPorUsername(String username);
    Empleado validarLogin(String username, String password);
    boolean existeUsername(String username);
    java.util.List<Empleado> obtenerTodos();
    boolean actualizar(Empleado empleado);
    boolean eliminar(int id);
}