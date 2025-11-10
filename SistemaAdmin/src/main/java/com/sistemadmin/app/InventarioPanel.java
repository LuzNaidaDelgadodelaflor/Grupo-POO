package com.sistemadmin.app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class InventarioPanel extends JPanel {
    private JTable tabla;
    private DefaultTableModel modelo;
    private JComboBox<String> comboTipo;

    public InventarioPanel() {
        setLayout(null);
        setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Inventario");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setBounds(400, 10, 200, 40);
        add(lblTitulo);

        JLabel lblProductos = new JLabel("Productos:");
        lblProductos.setFont(new Font("Arial", Font.PLAIN, 16));
        lblProductos.setBounds(50, 60, 100, 25);
        add(lblProductos);

        comboTipo = new JComboBox<>(new String[]{"Donados", "Comprados", "Todos"});
        comboTipo.setBounds(150, 60, 150, 25);
        add(comboTipo);

        JTextField txtBuscar = new JTextField();
        txtBuscar.setBounds(650, 60, 180, 25);
        add(txtBuscar);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(835, 60, 90, 25);
        add(btnBuscar);

        modelo = new DefaultTableModel(new String[]{"Código", "Nombre", "Existencias", "Precio", "Total", "Proveedor"}, 0);
        tabla = new JTable(modelo);
        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBounds(50, 100, 850, 200);
        add(scrollTabla);

        JButton btnInforme = new JButton("\uD83D\uDCDD Informe Mensual");
        btnInforme.setBounds(400, 310, 200, 30);
        add(btnInforme);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(600, 360, 100, 30);
        add(btnEliminar);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(710, 360, 100, 30);
        add(btnRegistrar);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(820, 360, 100, 30);
        add(btnActualizar);

        btnBuscar.addActionListener(e -> cargarProductos(txtBuscar.getText().trim()));
        comboTipo.addActionListener(e -> cargarProductos(txtBuscar.getText().trim()));
        btnRegistrar.addActionListener(e -> registrarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnActualizar.addActionListener(e -> cargarProductos(""));

        cargarProductos("");
    }

    private void cargarProductos(String filtro) {
        modelo.setRowCount(0);
        String tipo = comboTipo.getSelectedItem().toString();
        String sql;

        try (Connection conn = getConnection()) {
            if (tipo.equals("Todos")) {
                sql = "SELECT * FROM InventarioDonados WHERE nombre LIKE ? UNION ALL SELECT * FROM InventarioComprados WHERE nombre LIKE ?";
            } else if (tipo.equals("Donados")) {
                sql = "SELECT * FROM InventarioDonados WHERE nombre LIKE ?";
            } else {
                sql = "SELECT * FROM InventarioComprados WHERE nombre LIKE ?";
            }

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + filtro + "%");
            if (tipo.equals("Todos")) stmt.setString(2, "%" + filtro + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("existencias"),
                        rs.getString("precio"),
                        rs.getString("total"),
                        rs.getString("proveedor")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar productos: " + ex.getMessage());
        }
    }

    private void registrarProducto() {
        JTextField codigo = new JTextField();
        JTextField nombre = new JTextField();
        JTextField existencias = new JTextField();
        JTextField precio = new JTextField();
        JTextField total = new JTextField();
        JTextField proveedor = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Código:")); panel.add(codigo);
        panel.add(new JLabel("Nombre:")); panel.add(nombre);
        panel.add(new JLabel("Existencias:")); panel.add(existencias);
        panel.add(new JLabel("Precio:")); panel.add(precio);
        panel.add(new JLabel("Total:")); panel.add(total);
        panel.add(new JLabel("Proveedor:")); panel.add(proveedor);

        int result = JOptionPane.showConfirmDialog(null, panel, "Registrar Producto", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try (Connection conn = getConnection()) {
                String tipo = comboTipo.getSelectedItem().toString();
                String tabla = tipo.equals("Comprados") ? "InventarioComprados" : "InventarioDonados";
                String sql = "INSERT INTO " + tabla + " (codigo, nombre, existencias, precio, total, proveedor) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, codigo.getText().trim());
                stmt.setString(2, nombre.getText().trim());
                stmt.setString(3, existencias.getText().trim());
                stmt.setString(4, precio.getText().trim());
                stmt.setString(5, total.getText().trim());
                stmt.setString(6, proveedor.getText().trim());
                stmt.executeUpdate();
                cargarProductos("");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al registrar: " + ex.getMessage());
            }
        }
    }

    private void eliminarProducto() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para eliminar.");
            return;
        }
        String codigo = modelo.getValueAt(fila, 0).toString();
        String tipo = comboTipo.getSelectedItem().toString();
        String tabla = tipo.equals("Comprados") ? "InventarioComprados" : "InventarioDonados";

        int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas eliminar el producto " + codigo + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = getConnection()) {
                String sql = "DELETE FROM " + tabla + " WHERE codigo = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, codigo);
                stmt.executeUpdate();
                cargarProductos("");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage());
            }
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:sqlserver://localhost:1433;databaseName=SistemaOlla;encrypt=true;trustServerCertificate=true",
                "sa", "tumamonada1526");
    }
}
