package com.vet.Database.dao.Interfaces;

import com.vet.modelo.Mascotas.Mascota;


public interface MascotaDAO {

    boolean crear(Mascota mascota);
    Mascota buscarPorId(int id);
    java.util.List<Mascota> obtenerTodos();
    boolean actualizar(Mascota mascota);
    boolean eliminar(int id);
}