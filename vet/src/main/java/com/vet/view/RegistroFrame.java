package com.vet.view;

import com.vet.controlador.RegistroController;

import javax.swing.*;
import java.awt.*;

public class RegistroFrame extends JFrame {

    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmarPassword;
    private JButton btnRegistrar;
    private JButton btnCancelar;
    private RegistroController controller;
    private LoginFrame loginFrame;

    public RegistroFrame(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
        this.controller = new RegistroController();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Registro de Empleado");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel del título
        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = new JLabel("REGISTRO DE EMPLEADO");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelTitulo.add(lblTitulo);

        // Panel del formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtNombre = new JTextField(20);
        panelFormulario.add(txtNombre, gbc);

        // Apellido
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelFormulario.add(new JLabel("Apellido:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtApellido = new JTextField(20);
        panelFormulario.add(txtApellido, gbc);

        // Usuario
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panelFormulario.add(new JLabel("Usuario:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtUsername = new JTextField(20);
        panelFormulario.add(txtUsername, gbc);

        // Contraseña
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        panelFormulario.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtPassword = new JPasswordField(20);
        panelFormulario.add(txtPassword, gbc);

        // Confirmar contraseña
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        panelFormulario.add(new JLabel("Confirmar Contraseña:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtConfirmarPassword = new JPasswordField(20);
        panelFormulario.add(txtConfirmarPassword, gbc);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setPreferredSize(new Dimension(150, 30));
        btnRegistrar.addActionListener(e -> registrarEmpleado());

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(150, 30));
        btnCancelar.addActionListener(e -> cancelar());

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnCancelar);

        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private void registrarEmpleado() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        String confirmarPassword = new String(txtConfirmarPassword.getPassword());

        // Validar campos vacíos
        if (controller.hayCamposVacios(nombre, apellido, username, password, confirmarPassword)) {
            JOptionPane.showMessageDialog(this,
                    "Por favor complete todos los campos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que el nombre no contenga números
        if (controller.nombreContieneNumeros(nombre, apellido)) {
            JOptionPane.showMessageDialog(this,
                    "El nombre y apellido no pueden contener números",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar longitud del username
        if (!controller.usernameValido(username)) {
            JOptionPane.showMessageDialog(this,
                    "El usuario debe tener al menos 4 caracteres",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar longitud de la contraseña
        if (!controller.passwordValido(password)) {
            JOptionPane.showMessageDialog(this,
                    "La contraseña debe tener al menos 6 caracteres",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que las contraseñas coincidan
        if (!controller.passwordsCoinciden(password, confirmarPassword)) {
            JOptionPane.showMessageDialog(this,
                    "Las contraseñas no coinciden",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            txtConfirmarPassword.setText("");
            txtPassword.setText("");
            txtPassword.requestFocus();
            return;
        }

        // Verificar si el usuario ya existe
        if (controller.usernameExiste(username)) {
            JOptionPane.showMessageDialog(this,
                    "El nombre de usuario ya está en uso",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            txtUsername.requestFocus();
            return;
        }

        // Registrar el empleado
        if (controller.registrarEmpleado(nombre, apellido, username, password)) {
            JOptionPane.showMessageDialog(this,
                    "¡Empleado registrado exitosamente!\n" +
                            "Ya puede iniciar sesión con su usuario y contraseña",
                    "Registro Exitoso",
                    JOptionPane.INFORMATION_MESSAGE);

            volverAlLogin();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error al registrar el empleado. Intente nuevamente.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelar() {
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea cancelar el registro?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            volverAlLogin();
        }
    }

    private void volverAlLogin() {
        loginFrame.setVisible(true);
        this.dispose();
    }
}