package com.sistemadmin.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class EventosPanel extends JPanel {
    private JTextField txtBuscar, txtInicio, txtFin;
    private JTextArea areaConsulta;

    public EventosPanel() {
        setLayout(null);
        setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Eventos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBounds(300, 20, 400, 30);
        add(titulo);

        txtBuscar = new JTextField();
        txtBuscar.setBounds(200, 70, 400, 30);
        add(txtBuscar);

        JButton btnBuscar = new JButton("🔍");
        btnBuscar.setBounds(610, 70, 50, 30);
        add(btnBuscar);

        btnBuscar.addActionListener(e -> buscarEventos());

        JLabel calendario = new JLabel();
        ImageIcon calendarioImg = new ImageIcon("D:/FotosJava/calendario2025.jpg");
        calendario.setIcon(new ImageIcon(calendarioImg.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH)));
        calendario.setBounds(50, 130, 250, 250);
        add(calendario);

        JLabel lblFechaInicio = new JLabel("Fecha de inicio:");
        lblFechaInicio.setBounds(350, 130, 150, 25);
        add(lblFechaInicio);

        txtInicio = new JTextField();
        txtInicio.setBounds(470, 130, 150, 25);
        add(txtInicio);

        JLabel lblFechaFin = new JLabel("Fecha de fin:");
        lblFechaFin.setBounds(350, 170, 150, 25);
        add(lblFechaFin);

        txtFin = new JTextField();
        txtFin.setBounds(470, 170, 150, 25);
        add(txtFin);

        JButton btnRegistrar = new JButton("Registrar evento");
        btnRegistrar.setBounds(350, 210, 270, 30);
        add(btnRegistrar);

        btnRegistrar.addActionListener(e -> registrarEvento());

        JLabel lblConsulta = new JLabel("Consulta de eventos:");
        lblConsulta.setBounds(350, 250, 200, 25);
        add(lblConsulta);

        areaConsulta = new JTextArea();
        JScrollPane scroll = new JScrollPane(areaConsulta);
        scroll.setBounds(350, 280, 300, 100);
        add(scroll);

        JButton btnHistorial = new JButton("Mostrar historial");
        btnHistorial.setBounds(470, 390, 180, 30);
        add(btnHistorial);

        btnHistorial.addActionListener(e -> mostrarHistorial());
    }

    private void buscarEventos() {
        String busqueda = txtBuscar.getText().trim();
        areaConsulta.setText("");
        if (busqueda.isEmpty()) return;

        try (Connection conn = DriverManager.getConnection(
                "jdbc:sqlserver://localhost:1433;databaseName=SistemaOlla;encrypt=true;trustServerCertificate=true",
                "sa", "tumamonada1526")) {

            String sql = "SELECT * FROM Eventos WHERE nombre LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + busqueda + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                areaConsulta.append("Evento: " + rs.getString("nombre") + " | Inicio: " + rs.getString("fecha_inicio") +
                        " | Fin: " + rs.getString("fecha_fin") + "\n");
            }
        } catch (SQLException ex) {
            areaConsulta.setText("Error al buscar eventos: " + ex.getMessage());
        }
    }

    private void registrarEvento() {
        String fechaInicio = txtInicio.getText().trim();
        String fechaFin = txtFin.getText().trim();

        if (fechaInicio.isEmpty() || fechaFin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa ambas fechas para registrar un evento.");
            return;
        }

        try {
            LocalDate inicio = LocalDate.parse(fechaInicio);
            LocalDate fin = LocalDate.parse(fechaFin);

            if (fin.isBefore(inicio)) {
                JOptionPane.showMessageDialog(this, "La fecha de fin no puede ser anterior a la de inicio.");
                return;
            }
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Usa yyyy-MM-dd (ej: 2025-07-04).");
            return;
        }

        String nombre = JOptionPane.showInputDialog(this, "Ingrese nombre del evento:");
        if (nombre == null || nombre.trim().isEmpty()) return;

        try (Connection conn = DriverManager.getConnection(
                "jdbc:sqlserver://localhost:1433;databaseName=SistemaOlla;encrypt=true;trustServerCertificate=true",
                "sa", "tumamonada1526")) {

            String sql = "INSERT INTO Eventos (nombre, fecha_inicio, fecha_fin) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setString(2, fechaInicio);
            stmt.setString(3, fechaFin);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "✅ Evento registrado correctamente.");
            txtInicio.setText("");
            txtFin.setText("");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "❌ Error al registrar evento: " + ex.getMessage());
        }
    }

    private void mostrarHistorial() {
        areaConsulta.setText("");
        try (Connection conn = DriverManager.getConnection(
                "jdbc:sqlserver://localhost:1433;databaseName=SistemaOlla;encrypt=true;trustServerCertificate=true",
                "sa", "tumamonada1526")) {

            String sql = "SELECT * FROM Eventos ORDER BY fecha_inicio DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                areaConsulta.append("Evento: " + rs.getString("nombre") + " | Inicio: " + rs.getString("fecha_inicio") +
                        " | Fin: " + rs.getString("fecha_fin") + "\n");
            }
        } catch (SQLException ex) {
            areaConsulta.setText("Error al cargar historial: " + ex.getMessage());
        }
    }
}
