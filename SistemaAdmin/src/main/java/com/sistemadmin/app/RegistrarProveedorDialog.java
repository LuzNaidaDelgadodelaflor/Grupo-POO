package com.sistemadmin.app;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrarProveedorDialog extends JDialog {
    public RegistrarProveedorDialog(JFrame parent) {
        super(parent, "Registrar Proveedor", true);
        setSize(400, 300);
        setLayout(null);
        setLocationRelativeTo(parent);

        JLabel lblNombre = new JLabel("Nombre y Apellido:");
        lblNombre.setBounds(30, 20, 150, 25);
        add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(180, 20, 170, 25);
        add(txtNombre);

        JLabel lblProducto = new JLabel("Producto:");
        lblProducto.setBounds(30, 60, 150, 25);
        add(lblProducto);

        JTextField txtProducto = new JTextField();
        txtProducto.setBounds(180, 60, 170, 25);
        add(txtProducto);

        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setBounds(30, 100, 150, 25);
        add(lblCantidad);

        JTextField txtCantidad = new JTextField();
        txtCantidad.setBounds(180, 100, 170, 25);
        add(txtCantidad);

        JLabel lblActivo = new JLabel("¿Activo?");
        lblActivo.setBounds(30, 140, 150, 25);
        add(lblActivo);

        String[] opcionesActivo = {"Sí", "No"};
        JComboBox<String> comboActivo = new JComboBox<>(opcionesActivo);
        comboActivo.setBounds(180, 140, 170, 25);
        add(comboActivo);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(80, 200, 100, 30);
        add(btnRegistrar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(200, 200, 100, 30);
        add(btnCancelar);

        btnCancelar.addActionListener(e -> dispose());

        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String producto = txtProducto.getText().trim();
            String cantidad = txtCantidad.getText().trim();
            String activo = (String) comboActivo.getSelectedItem();

            if (nombre.isEmpty() || producto.isEmpty() || cantidad.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.");
                return;
            }

            // Conexión a la base de datos
            String url = "jdbc:sqlserver://localhost:1433;databaseName=SistemaOlla;encrypt=true;trustServerCertificate=true";
            String user = "sa";
            String password = "tumamonada1526";

            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                String query = "INSERT INTO Proveedores (nombre_apellido, producto, cantidad, activo) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, nombre);
                stmt.setString(2, producto);
                stmt.setString(3, cantidad);
                stmt.setString(4, activo);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "✅ Proveedor registrado exitosamente.");
                dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "❌ Error al registrar proveedor: " + ex.getMessage());
            }
        });

        setVisible(true);
    }
}
