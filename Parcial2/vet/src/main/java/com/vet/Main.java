package com.vet;

import com.vet.view.LoginFrame;
import com.vet.database.DatabaseConnection;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Iniciando Sistema de Adopción de Mascotas");
        DatabaseConnection.getConnection();
        System.out.println("Sistema iniciado correctamente:");

        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}