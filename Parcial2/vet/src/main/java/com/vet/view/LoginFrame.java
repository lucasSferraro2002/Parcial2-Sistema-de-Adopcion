package com.vet.view;

import com.vet.modelo.Empleado;
import com.vet.controlador.LoginController;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegistro;
    private LoginController controller;

    public LoginFrame() {
        controller = new LoginController();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Sistema de Adopción - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel del título
        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = new JLabel("SISTEMA DE ADOPCIÓN");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelTitulo.add(lblTitulo);

        // Panel del formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Usuario
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblUsername = new JLabel("Usuario:");
        panelFormulario.add(lblUsername, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        txtUsername = new JTextField(20);
        panelFormulario.add(txtUsername, gbc);

        // Contraseña
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel lblPassword = new JLabel("Contraseña:");
        panelFormulario.add(lblPassword, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        txtPassword = new JPasswordField(20);
        panelFormulario.add(txtPassword, gbc);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setPreferredSize(new Dimension(150, 30));
        btnLogin.addActionListener(e -> iniciarSesion());

        btnRegistro = new JButton("Registrarse");
        btnRegistro.setPreferredSize(new Dimension(150, 30));
        btnRegistro.addActionListener(e -> abrirRegistro());

        panelBotones.add(btnLogin);
        panelBotones.add(btnRegistro);

        // Agregar paneles al principal
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);

        // Enter para login
        txtPassword.addActionListener(e -> iniciarSesion());
    }

    private void iniciarSesion() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        // Validar campos vacíos
        if (controller.hayCamposVacios(username, password)) {
            JOptionPane.showMessageDialog(this,
                    "Por favor complete todos los campos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar credenciales
        Empleado empleado = controller.validarCredenciales(username, password);

        if (empleado != null) {
            // Login exitoso
            JOptionPane.showMessageDialog(this,
                    "¡Bienvenido " + empleado.obtenerNombreCompleto() + "!",
                    "Login Exitoso",
                    JOptionPane.INFORMATION_MESSAGE);

            // Cerrar login y abrir la ventana principal
            this.dispose();
            abrirVentanaPrincipal();
        } else {
            // Login fallido
            JOptionPane.showMessageDialog(this,
                    "Usuario o contraseña incorrectos",
                    "Error de Login",
                    JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
            txtUsername.requestFocus();
        }
    }

    private void abrirRegistro() {
        RegistroFrame registroFrame = new RegistroFrame(this);
        registroFrame.setVisible(true);
        this.setVisible(false);
    }

    private void abrirVentanaPrincipal() {
        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);
        });
    }
}