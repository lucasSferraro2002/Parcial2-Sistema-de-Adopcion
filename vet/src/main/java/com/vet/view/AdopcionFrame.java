package com.vet.view;

import com.vet.modelo.Adopcion;
import com.vet.controlador.AdopcionController;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.util.Date;

public class AdopcionFrame extends JFrame {

    // Componentes del formulario de Adoptante
    private JTextField txtNombreAdoptante;
    private JTextField txtEdad;
    private JTextField txtDireccion;

    // Componentes del formulario de Mascota
    private JTextField txtNombreMascota;
    private JTextField txtFechaNacimiento;
    private JTextField txtPeso;
    private JComboBox<String> cmbEspecie;
    private JTextField txtAtributoEspecial;
    private JLabel lblAtributoEspecial;

    // Botones
    private JButton btnRegistrar;
    private JButton btnCancelar;
    private JTextArea txtCuidados;

    // Controlador
    private AdopcionController controller;

    private MainWindow mainWindow;

    public AdopcionFrame(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.controller = new AdopcionController();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Registro de Adopción");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal con scroll
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel lblTitulo = new JLabel("REGISTRO DE ADOPCIÓN");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblTitulo);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));

        // Sección Adoptante
        panelPrincipal.add(crearPanelAdoptante());
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));

        // Sección Mascota
        panelPrincipal.add(crearPanelMascota());
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));

        // Sección Cuidados
        panelPrincipal.add(crearPanelCuidados());
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));

        // Botones
        panelPrincipal.add(crearPanelBotones());

        // Agregar scroll
        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);
    }

    private JPanel crearPanelAdoptante() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Adoptante"));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nombre completo:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtNombreAdoptante = new JTextField(25);
        panel.add(txtNombreAdoptante, gbc);

        // Edad
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Edad:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtEdad = new JTextField(25);
        panel.add(txtEdad, gbc);

        // Dirección
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panel.add(new JLabel("Dirección:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtDireccion = new JTextField(25);
        panel.add(txtDireccion, gbc);

        return panel;
    }

    private JPanel crearPanelMascota() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Datos de la Mascota"));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Especie
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Especie:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        java.util.List<String> especies = java.util.Arrays.asList("Perro", "Gato", "Conejo", "Ave");
        cmbEspecie = new JComboBox<>(especies.toArray(new String[0]));
        cmbEspecie.addActionListener(e -> actualizarAtributoEspecial());
        panel.add(cmbEspecie, gbc);

        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtNombreMascota = new JTextField(25);
        panel.add(txtNombreMascota, gbc);

        // Fecha de nacimiento
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panel.add(new JLabel("Fecha nacimiento (dd/MM/yyyy):"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtFechaNacimiento = new JTextField(25);
        panel.add(txtFechaNacimiento, gbc);

        // Peso
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        panel.add(new JLabel("Peso (kg):"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtPeso = new JTextField(25);
        panel.add(txtPeso, gbc);

        // Atributo especial (dinámico)
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        lblAtributoEspecial = new JLabel("Tamaño:");
        panel.add(lblAtributoEspecial, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtAtributoEspecial = new JTextField(25);
        panel.add(txtAtributoEspecial, gbc);

        return panel;
    }

    private JPanel crearPanelCuidados() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Recomendaciones de Cuidado"));
        panel.setLayout(new BorderLayout());

        txtCuidados = new JTextArea(5, 40);
        txtCuidados.setEditable(false);
        txtCuidados.setLineWrap(true);
        txtCuidados.setWrapStyleWord(true);
        txtCuidados.setFont(new Font("Arial", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(txtCuidados);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton btnVerCuidados = new JButton("Ver Recomendaciones");
        btnVerCuidados.addActionListener(e -> mostrarCuidados());
        panel.add(btnVerCuidados, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        btnRegistrar = new JButton("Registrar Adopción");
        btnRegistrar.setPreferredSize(new Dimension(180, 40));
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrar.addActionListener(e -> registrarAdopcion());

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(180, 40));
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.addActionListener(e -> cancelar());

        panel.add(btnRegistrar);
        panel.add(btnCancelar);

        return panel;
    }

    private void actualizarAtributoEspecial() {
        String especie = (String) cmbEspecie.getSelectedItem();
        switch (especie) {
            case "Perro":
                lblAtributoEspecial.setText("Tamaño:");
                txtAtributoEspecial.setToolTipText("pequeño/mediano/grande");
                break;
            case "Gato":
                lblAtributoEspecial.setText("Temperamento:");
                txtAtributoEspecial.setToolTipText("tranquilo/juguetón/independiente");
                break;
            case "Conejo":
                lblAtributoEspecial.setText("Color:");
                txtAtributoEspecial.setToolTipText("Ingrese el color del conejo");
                break;
            case "Ave":
                lblAtributoEspecial.setText("Tipo:");
                txtAtributoEspecial.setToolTipText("loro/canario/periquito...");
                break;
        }
    }

    private void mostrarCuidados() {
        String especie = (String) cmbEspecie.getSelectedItem();
        String cuidados = controller.obtenerRecomendacionesCuidado(especie);
        txtCuidados.setText(cuidados);
    }

    private void registrarAdopcion() {
        try {
            // Obtener datos del adoptante
            String nombreAdoptante = txtNombreAdoptante.getText().trim();
            String edadStr = txtEdad.getText().trim();
            String direccion = txtDireccion.getText().trim();

            // Validar campos del adoptante
            if (controller.hayCamposVaciosAdoptante(nombreAdoptante, edadStr, direccion)) {
                JOptionPane.showMessageDialog(this,
                        "Complete todos los campos del adoptante",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!controller.validarNombreAdoptante(nombreAdoptante)) {
                JOptionPane.showMessageDialog(this,
                        "El nombre no puede contener números",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!controller.validarEdad(edadStr)) {
                JOptionPane.showMessageDialog(this,
                        "La edad debe estar entre 18 y 100 años",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int edad = controller.parsearEdad(edadStr);

            // Obtener datos de la mascota
            String nombreMascota = txtNombreMascota.getText().trim();
            String fechaStr = txtFechaNacimiento.getText().trim();
            String pesoStr = txtPeso.getText().trim();
            String atributoEspecial = txtAtributoEspecial.getText().trim();
            String especie = (String) cmbEspecie.getSelectedItem();

            // Validar campos de la mascota
            if (controller.hayCamposVaciosMascota(nombreMascota, fechaStr, pesoStr, atributoEspecial)) {
                JOptionPane.showMessageDialog(this,
                        "Complete todos los campos de la mascota",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Date fechaNacimiento;
            try {
                fechaNacimiento = controller.parsearFecha(fechaStr);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this,
                        "Formato de fecha inválido. Use dd/MM/yyyy",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!controller.validarPeso(pesoStr)) {
                JOptionPane.showMessageDialog(this,
                        "El peso debe ser un número mayor a 0",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            double peso = controller.parsearPeso(pesoStr);

            // Registrar la adopción usando el controlador
            Adopcion adopcion = controller.registrarAdopcion(
                    nombreAdoptante, edad, direccion,
                    nombreMascota, especie, fechaNacimiento, peso, atributoEspecial
            );

            if (adopcion != null) {
                // Mostrar ticket
                TicketFrame ticketFrame = new TicketFrame(adopcion, this);
                ticketFrame.setVisible(true);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al registrar la adopción",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void cancelar() {
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea cancelar el registro?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            volverAlMenu();
        }
    }

    public void volverAlMenu() {
        mainWindow.setVisible(true);
        this.dispose();
    }
}