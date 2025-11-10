package com.sistemadmin.app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class ProveedoresPanel extends JPanel {
    private DefaultTableModel model;
    private JTable tabla;

    public ProveedoresPanel() {
        setLayout(null);
        setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Proveedores");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBounds(380, 20, 300, 30);
        add(titulo);

        // Buscar
        JTextField txtBuscar = new JTextField("Buscar");
        txtBuscar.setBounds(240, 70, 400, 30);
        add(txtBuscar);

        JButton btnBuscar = new JButton("🔍");
        btnBuscar.setBounds(650, 70, 50, 30);
        btnBuscar.setBackground(new Color(39, 127, 179));
        btnBuscar.setForeground(Color.WHITE);
        add(btnBuscar);

        // Tabla
        String[] columnas = {"ID", "Nombre y Apellido", "Producto", "Cantidad", "Activo"};
        model = new DefaultTableModel(columnas, 0);
        tabla = new JTable(model);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(100, 120, 800, 150);
        add(scroll);

        // Botones
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(420, 290, 100, 30);
        add(btnEliminar);

        JButton btnEditar = new JButton("Editar");
        btnEditar.setBounds(530, 290, 100, 30);
        add(btnEditar);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(640, 290, 100, 30);
        add(btnRegistrar);

        btnRegistrar.addActionListener(e -> {
            new RegistrarProveedorDialog((JFrame) SwingUtilities.getWindowAncestor(this));
            cargarDatos(); // Recargar datos después del registro
        });

        JButton btnHistorial = new JButton("📦 Historial de proveedores");
        btnHistorial.setBounds(330, 350, 300, 40);
        btnHistorial.setBackground(new Color(230, 230, 230));
        btnHistorial.setFocusPainted(false);
        add(btnHistorial);

        // Cargar datos desde la BD
        cargarDatos();
    }

    private void cargarDatos() {
        model.setRowCount(0); // Limpiar la tabla

        String url = "jdbc:sqlserver://localhost:1433;databaseName=SistemaOlla;encrypt=true;trustServerCertificate=true";
        String user = "sa";
        String pass = "tumamonada1526";

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String query = "SELECT * FROM Proveedores";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Vector<Object> fila = new Vector<>();
                fila.add(rs.getInt("id"));
                fila.add(rs.getString("nombre_apellido"));
                fila.add(rs.getString("producto"));
                fila.add(rs.getString("cantidad"));
                fila.add(rs.getString("activo"));
                model.addRow(fila);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "❌ Error al cargar datos: " + ex.getMessage());
        }
    }
}
    