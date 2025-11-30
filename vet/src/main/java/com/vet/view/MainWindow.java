package com.vet.view;

import com.vet.modelo.Empleado;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private JLabel lblBienvenida;
    private JButton btnNuevaAdopcion;
    private JButton btnVerAdopciones;
    private JButton btnCerrarSesion;

    public MainWindow() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Sistema de Adopción - Menú Principal");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel superior
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("SISTEMA DE ADOPCIÓN DE MASCOTAS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        Empleado empleadoLogueado = Empleado.getInstanciaLogueada();
        lblBienvenida = new JLabel("Bienvenido: " + empleadoLogueado.obtenerNombreCompleto());
        lblBienvenida.setFont(new Font("Arial", Font.PLAIN, 14));
        lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);

        panelSuperior.add(lblTitulo, BorderLayout.NORTH);
        panelSuperior.add(lblBienvenida, BorderLayout.CENTER);

        // Panel central con botones
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Botón para nueva adopción
        btnNuevaAdopcion = new JButton("Registrar Nueva Adopción");
        btnNuevaAdopcion.setPreferredSize(new Dimension(250, 50));
        btnNuevaAdopcion.setFont(new Font("Arial", Font.BOLD, 14));
        btnNuevaAdopcion.addActionListener(e -> abrirNuevaAdopcion());

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCentral.add(btnNuevaAdopcion, gbc);

        // Botón para ver adopciones
        btnVerAdopciones = new JButton("Ver Historial de Adopciones");
        btnVerAdopciones.setPreferredSize(new Dimension(250, 50));
        btnVerAdopciones.setFont(new Font("Arial", Font.BOLD, 14));
        btnVerAdopciones.addActionListener(e -> verAdopciones());

        gbc.gridy = 1;
        panelCentral.add(btnVerAdopciones, gbc);

        // Panel inferior
        JPanel panelInferior = new JPanel();
        btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setPreferredSize(new Dimension(150, 30));
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        panelInferior.add(btnCerrarSesion);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private void abrirNuevaAdopcion() {
        AdopcionFrame adopcionFrame = new AdopcionFrame(this);
        adopcionFrame.setVisible(true);
        this.setVisible(false);
    }

    private void verAdopciones() {
        HistorialAdopcionesFrame historialFrame = new HistorialAdopcionesFrame(this);
        historialFrame.setVisible(true);
        this.setVisible(false);
    }

    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea cerrar sesión?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            Empleado.cerrarSesion();
            this.dispose();

            // Volver al login
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        }
    }
}