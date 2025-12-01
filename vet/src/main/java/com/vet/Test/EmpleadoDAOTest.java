package com.vet.test;

import com.vet.Database.dao.Interfaces.EmpleadoDAO;
import com.vet.Database.dao.Implenentacion.EmpleadoDAOH2Impl;
import com.vet.modelo.Empleado;

import java.util.List;
import java.util.Random;

/**
 * Test unitario para EmpleadoDAOH2Impl
 * Basado en tu implementación actual
 */
public class EmpleadoDAOTest {

    private static int empleadoIdCreado = -1;
    private static String usernameTest;

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   TEST - EmpleadoDAOH2Impl");
        System.out.println("========================================\n");

        // Generar username único para evitar colisiones
        usernameTest = "testuser" + new Random().nextInt(10000);

        EmpleadoDAO dao = new EmpleadoDAOH2Impl();

        // TEST 1: Crear empleado
        testCrearEmpleado(dao);

        // TEST 2: Buscar por ID
        testBuscarPorId(dao);

        // TEST 3: Buscar por username
        testBuscarPorUsername(dao);

        // TEST 4: Validar login correcto
        testValidarLoginCorrecto(dao);

        // TEST 5: Validar login incorrecto
        testValidarLoginIncorrecto(dao);

        // TEST 6: Verificar username existe
        testUsernameExiste(dao);

        // TEST 7: Obtener todos los empleados
        testObtenerTodos(dao);

        // TEST 8: Actualizar empleado
        testActualizarEmpleado(dao);

        // TEST 9: Eliminar empleado
        testEliminarEmpleado(dao);

        System.out.println("   TESTS COMPLETADOS");
    }

    private static void testCrearEmpleado(EmpleadoDAO dao) {
        System.out.println("TEST 1: Crear Empleado");


        Empleado empleado = new Empleado("Lucas", "Castro", usernameTest, "123456");
        boolean resultado = dao.crear(empleado);

        if (resultado && empleado.getId() > 0) {
            empleadoIdCreado = empleado.getId();
            System.out.println("  PASÓ: Empleado creado correctamente");
            System.out.println("  ID generado: " + empleado.getId());
            System.out.println("  Nombre: " + empleado.obtenerNombreCompleto());
            System.out.println("  Username: " + empleado.getUsername());
        } else {
            System.out.println("  FALLÓ: No se pudo crear el empleado");
        }
        System.out.println();
    }

    private static void testBuscarPorId(EmpleadoDAO dao) {
        System.out.println("TEST 2: Buscar por ID");

        if (empleadoIdCreado == -1) {
            System.out.println("  SALTADO: No hay empleado creado");
            System.out.println();
            return;
        }

        Empleado empleado = dao.buscarPorId(empleadoIdCreado);

        if (empleado != null && empleado.getId() == empleadoIdCreado) {
            System.out.println("  PASÓ: Empleado encontrado por ID");
            System.out.println("  ID: " + empleado.getId());
            System.out.println("  Nombre: " + empleado.obtenerNombreCompleto());
        } else {
            System.out.println("  FALLÓ: Empleado no encontrado");
        }
        System.out.println();
    }

    private static void testBuscarPorUsername(EmpleadoDAO dao) {
        System.out.println("TEST 3: Buscar por Username");

        Empleado empleado = dao.buscarPorUsername(usernameTest);

        if (empleado != null) {
            System.out.println("  PASÓ: Empleado encontrado por username");
            System.out.println("  Username: " + empleado.getUsername());
            System.out.println("  Nombre: " + empleado.obtenerNombreCompleto());
        } else {
            System.out.println("  FALLÓ: Empleado no encontrado");
        }
        System.out.println();
    }

    private static void testValidarLoginCorrecto(EmpleadoDAO dao) {
        System.out.println("TEST 4: Validar Login Correcto");

        Empleado empleado = dao.validarLogin(usernameTest, "123456");

        if (empleado != null) {
            System.out.println("  PASÓ: Login validado correctamente");
            System.out.println("  Empleado: " + empleado.obtenerNombreCompleto());
        } else {
            System.out.println("  FALLÓ: Login incorrecto");
        }
        System.out.println();
    }

    private static void testValidarLoginIncorrecto(EmpleadoDAO dao) {
        System.out.println("TEST 5: Validar Login Incorrecto");

        Empleado empleado = dao.validarLogin(usernameTest, "passwordIncorrecta");

        if (empleado == null) {
            System.out.println("  PASÓ: Login rechazado correctamente (password incorrecta)");
        } else {
            System.out.println("  FALLÓ: Login aceptado con password incorrecta");
        }
        System.out.println();
    }

    private static void testUsernameExiste(EmpleadoDAO dao) {
        System.out.println("TEST 6: Verificar Username Existe");

        boolean existe = dao.existeUsername(usernameTest);
        boolean noExiste = dao.existeUsername("usuarioInexistente999");

        if (existe && !noExiste) {
            System.out.println("  PASÓ: Verificación de username funciona correctamente");
            System.out.println("  '" + usernameTest + "' existe: " + existe);
            System.out.println("  'usuarioInexistente999' existe: " + noExiste);
        } else {
            System.out.println("  FALLÓ: Verificación de username incorrecta");
        }
        System.out.println();
    }

    private static void testObtenerTodos(EmpleadoDAO dao) {
        System.out.println("TEST 7: Obtener Todos los Empleados");

        List<Empleado> empleados = dao.obtenerTodos();

        if (empleados != null && !empleados.isEmpty()) {
            System.out.println("  PASÓ: Se obtuvieron " + empleados.size() + " empleado(s)");
            int mostrar = Math.min(5, empleados.size());
            for (int i = 0; i < mostrar; i++) {
                Empleado emp = empleados.get(i);
                System.out.println("  - ID: " + emp.getId() + " | " + emp.obtenerNombreCompleto() + " (@" + emp.getUsername() + ")");
            }
            if (empleados.size() > 5) {
                System.out.println(" y " + (empleados.size() - 5) + " más");
            }
        } else {
            System.out.println("  FALLÓ: No se encontraron empleados");
        }
        System.out.println();
    }

    private static void testActualizarEmpleado(EmpleadoDAO dao) {
        System.out.println("TEST 8: Actualizar Empleado");

        if (empleadoIdCreado == -1) {
            System.out.println("  SALTADO: No hay empleado creado");
            System.out.println();
            return;
        }

        Empleado empleado = dao.buscarPorId(empleadoIdCreado);
        if (empleado != null) {
            String nombreAnterior = empleado.obtenerNombreCompleto();
            empleado.setNombre("Juan Carlos");
            empleado.setApellido("Pérez González");

            boolean resultado = dao.actualizar(empleado);

            if (resultado) {
                Empleado empleadoActualizado = dao.buscarPorId(empleadoIdCreado);
                System.out.println("  PASÓ: Empleado actualizado correctamente");
                System.out.println("  Nombre anterior: " + nombreAnterior);
                System.out.println("  Nombre nuevo: " + empleadoActualizado.obtenerNombreCompleto());
            } else {
                System.out.println("  FALLÓ: No se pudo actualizar");
            }
        } else {
            System.out.println("  FALLÓ: No se encontró el empleado para actualizar");
        }
        System.out.println();
    }

    private static void testEliminarEmpleado(EmpleadoDAO dao) {
        System.out.println("TEST 9: Eliminar Empleado");

        if (empleadoIdCreado == -1) {
            System.out.println("  SALTADO: No hay empleado creado");
            System.out.println();
            return;
        }

        boolean resultado = dao.eliminar(empleadoIdCreado);

        if (resultado) {
            Empleado empleadoEliminado = dao.buscarPorId(empleadoIdCreado);
            if (empleadoEliminado == null) {
                System.out.println("  PASÓ: Empleado eliminado correctamente");
                System.out.println("  ID eliminado: " + empleadoIdCreado);
            } else {
                System.out.println("  FALLÓ: El empleado aún existe después de eliminar");
            }
        } else {
            System.out.println("  FALLÓ: No se pudo eliminar el empleado");
        }
        System.out.println();
    }
}