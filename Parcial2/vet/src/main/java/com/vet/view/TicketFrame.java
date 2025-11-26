package com.vet.view;

import com.vet.modelo.Adopcion;
import com.vet.modelo.GeneradorTicket;

import javax.swing.*;
import java.awt.*;

public class TicketFrame extends JFrame {

    private JTextArea txtTicket;
    private JButton btnVolver;
    private Adopcion adopcion;
    private AdopcionFrame adopcionFrame;

    public TicketFrame(Adopcion adopcion, AdopcionFrame adopcionFrame) {
        this.adopcion = adopcion;
        this.adopcionFrame = adopcionFrame;
        inicializarComponentes();
        mostrarTicket();
    }

    private void inicializarComponentes() {
        setTitle("Ticket de Adopción");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = new JLabel("¡ADOPCIÓN REGISTRADA EXITOSAMENTE!");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(0, 128, 0));
        panelTitulo.add(lblTitulo);

        // Área de texto
        txtTicket = new JTextArea();
        txtTicket.setEditable(false);
        txtTicket.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtTicket.setBackground(new Color(255, 255, 220));

        JScrollPane scrollPane = new JScrollPane(txtTicket);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        btnVolver = new JButton("Volver al Menú");
        btnVolver.setPreferredSize(new Dimension(170, 40));
        btnVolver.addActionListener(e -> volverAlMenu());

        panelBotones.add(btnVolver);
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private void mostrarTicket() {
        String ticket = GeneradorTicket.generarTicket(adopcion);
        txtTicket.setText(ticket);
    }

    private void volverAlMenu() {
        adopcionFrame.volverAlMenu();
        this.dispose();
    }
}