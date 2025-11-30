package com.vet.Database.dao.Interfaces;

import com.vet.modelo.Adoptante;

/**
 * Interfaz DAO para Adoptante
 */
public interface AdoptanteDAO {

    boolean crear(Adoptante adoptante);
    Adoptante buscarPorId(int id);
    java.util.List<Adoptante> obtenerTodos();
    boolean actualizar(Adoptante adoptante);
    boolean eliminar(int id);
}