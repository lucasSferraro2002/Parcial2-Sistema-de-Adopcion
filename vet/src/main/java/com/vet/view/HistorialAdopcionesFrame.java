package com.vet.view;

import com.vet.modelo.Adopcion;
import com.vet.controlador.HistorialController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class HistorialAdopcionesFrame extends JFrame {

    private JTable tablaAdopciones;
    private DefaultTableModel modeloTabla;
    private JButton btnVolver;
    private JButton btnVerDetalle;
    private HistorialController controller;
    private MainWindow mainWindow;
    private SimpleDateFormat formato;

    public HistorialAdopcionesFrame(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.controller = new HistorialController();
        this.formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        inicializarComponentes();
        cargarAdopciones();
    }

    private void inicializarComponentes() {
        setTitle("Historial de Adopciones");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = new JLabel("HISTORIAL DE ADOPCIONES");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelTitulo.add(lblTitulo);

        // Tabla
        java.util.List<String> columnas = java.util.Arrays.asList("Número", "Fecha", "Adoptante", "Mascota", "Especie", "Empleado");
        modeloTabla = new DefaultTableModel(columnas.toArray(new String[0]), 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No editable
            }
        };

        tablaAdopciones = new JTable(modeloTabla);
        tablaAdopciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaAdopciones.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tablaAdopciones);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        btnVerDetalle = new JButton("Ver Detalle");
        btnVerDetalle.setPreferredSize(new Dimension(150, 35));
        btnVerDetalle.addActionListener(e -> verDetalle());

        btnVolver = new JButton("Volver");
        btnVolver.setPreferredSize(new Dimension(150, 35));
        btnVolver.addActionListener(e -> volver());

        panelBotones.add(btnVerDetalle);
        panelBotones.add(btnVolver);

        // Agregar todo
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private void cargarAdopciones() {
        // Limpiar tabla
        modeloTabla.setRowCount(0);

        // Obtener adopciones usando el controlador
        List<Adopcion> adopciones = controller.obtenerAdopcionesEmpleadoLogueado();

        // Si no hay adopciones, mostrar mensaje
        if (adopciones.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay adopciones registradas aún",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Llenar la tabla
        for (Adopcion adopcion : adopciones) {
            Object[] fila = {
                    adopcion.getNumeroAdopcion(),
                    formato.format(adopcion.getFechaHoraAdopcion()),
                    adopcion.getAdoptante().getNombre(),
                    adopcion.getMascota().getNombre(),
                    adopcion.getMascota().getEspecie(),
                    adopcion.getEmpleado().obtenerNombreCompleto()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void verDetalle() {
        int filaSeleccionada = tablaAdopciones.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor seleccione una adopción",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el número de adopción
        String numeroAdopcion = (String) modeloTabla.getValueAt(filaSeleccionada, 0);

        // Buscar la adopción completa usando el controlador
        Adopcion adopcion = controller.buscarPorNumero(numeroAdopcion);

        if (adopcion != null) {
            // Mostrar el detalle
            DetalleAdopcionDialog dialog = new DetalleAdopcionDialog(this, adopcion);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo cargar el detalle de la adopción",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void volver() {
        mainWindow.setVisible(true);
        this.dispose();
    }
}

// Diálogo para mostrar detalle de adopción
class DetalleAdopcionDialog extends JDialog {

    public DetalleAdopcionDialog(JFrame parent, Adopcion adopcion) {
        super(parent, "Detalle de Adopción", true);
        setSize(500, 600);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextArea txtDetalle = new JTextArea();
        txtDetalle.setEditable(false);
        txtDetalle.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtDetalle.setText(com.vet.modelo.GeneradorTicket.generarTicket(adopcion));

        JScrollPane scrollPane = new JScrollPane(txtDetalle);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnCerrar);
        panel.add(panelBoton, BorderLayout.SOUTH);

        add(panel);
    }
}