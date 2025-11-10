package com.sistemadmin.app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RolesPanel extends JPanel {
    private JTable tabla;
    private DefaultTableModel modelo;

    public RolesPanel() {
        setLayout(null);
        setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Gestión de Roles", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBounds(300, 20, 400, 30);
        add(titulo);

        modelo = new DefaultTableModel(new String[]{"Nombre", "Apellido", "Rol", "Descripción"}, 0);
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(100, 70, 800, 300);
        add(scroll);

        JButton btnAgregar = new JButton("Agregar Rol");
        btnAgregar.setBounds(100, 390, 150, 30);
        add(btnAgregar);

        JButton btnEliminar = new JButton("Eliminar Rol");
        btnEliminar.setBounds(270, 390, 150, 30);
        add(btnEliminar);

        btnAgregar.addActionListener(e -> agregarRol());
        btnEliminar.addActionListener(e -> eliminarRol());

        cargarRoles();
    }

    private void cargarRoles() {
        modelo.setRowCount(0);
        try (Connection conn = getConnection()) {
            String sql = "SELECT nombre, apellido, rol, descripcion FROM Usuarios";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("rol"),
                    rs.getString("descripcion")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar roles: " + ex.getMessage());
        }
    }

    private void agregarRol() {
        JTextField nombreField = new JTextField();
        JTextField apellidoField = new JTextField();
        JTextField rolField = new JTextField();
        JTextField descripcionField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Apellido:"));
        panel.add(apellidoField);
        panel.add(new JLabel("Rol:"));
        panel.add(rolField);
        panel.add(new JLabel("Descripción:"));
        panel.add(descripcionField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Agregar nuevo rol",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try (Connection conn = getConnection()) {
                String sql = "INSERT INTO Usuarios (nombre, apellido, rol, descripcion) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, nombreField.getText().trim());
                stmt.setString(2, apellidoField.getText().trim());
                stmt.setString(3, rolField.getText().trim());
                stmt.setString(4, descripcionField.getText().trim());
                stmt.executeUpdate();
                cargarRoles();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al agregar rol: " + ex.getMessage());
            }
        }
    }

    private void eliminarRol() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.");
            return;
        }
        String nombre = modelo.getValueAt(fila, 0).toString();
        String apellido = modelo.getValueAt(fila, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de eliminar el rol de " + nombre + " " + apellido + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = getConnection()) {
                String sql = "DELETE FROM Usuarios WHERE nombre = ? AND apellido = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, nombre);
                stmt.setString(2, apellido);
                stmt.executeUpdate();
                cargarRoles();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar rol: " + ex.getMessage());
            }
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:sqlserver://localhost:1433;databaseName=SistemaOlla;encrypt=true;trustServerCertificate=true",
                "sa", "tumamonada1526");
    }
}
