package com.sistemadmin.app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class InformacionUsuariosPanel extends JPanel {
    private JTable tablaUsuarios;
    private DefaultTableModel modelo;
    private Connection conexion;
    private JTextField txtBuscar;

    public InformacionUsuariosPanel() {
        setLayout(null);
        setBackground(Color.WHITE);

        conectarBD();

        JLabel titulo = new JLabel("Información de los Usuarios");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBounds(300, 20, 400, 30);
        add(titulo);

        JLabel lblBuscar = new JLabel("Buscar por DNI o nombre:");
        lblBuscar.setBounds(100, 60, 200, 20);
        add(lblBuscar);

        txtBuscar = new JTextField();
        txtBuscar.setBounds(300, 60, 200, 25);
        add(txtBuscar);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(510, 60, 100, 25);
        add(btnBuscar);

        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "DNI", "Celular", "Rol"}, 0);
        tablaUsuarios = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tablaUsuarios);
        scroll.setBounds(100, 100, 800, 280);
        add(scroll);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(100, 400, 120, 30);
        add(btnActualizar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(230, 400, 120, 30);
        add(btnEliminar);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(360, 400, 120, 30);
        add(btnRegistrar);

        btnActualizar.addActionListener(e -> cargarUsuarios());

        btnBuscar.addActionListener(e -> buscarUsuarios(txtBuscar.getText().trim()));

        btnEliminar.addActionListener(e -> {
            int fila = tablaUsuarios.getSelectedRow();
            if (fila != -1) {
                int id = Integer.parseInt(tablaUsuarios.getValueAt(fila, 0).toString());
                eliminarUsuario(id);
                cargarUsuarios();
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un usuario para eliminar.");
            }
        });

        btnRegistrar.addActionListener(e -> mostrarFormularioRegistro());

        cargarUsuarios();
    }

    private void conectarBD() {
        try {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=SistemaOlla;encrypt=true;trustServerCertificate=true";
            String user = "sa";
            String password = "tumamonada1526";
            conexion = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error de conexión: " + e.getMessage());
        }
    }

    private void cargarUsuarios() {
        modelo.setRowCount(0);
        try {
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, nombre, dni, celular, rol FROM Usuarios");
            while (rs.next()) {
                modelo.addRow(new Object[]{rs.getInt("id"), rs.getString("nombre"), rs.getString("dni"), rs.getString("celular"), rs.getString("rol")});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + e.getMessage());
        }
    }

    private void buscarUsuarios(String criterio) {
        modelo.setRowCount(0);
        try {
            String sql = "SELECT id, nombre, dni, celular, rol FROM Usuarios WHERE nombre LIKE ? OR dni LIKE ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, "%" + criterio + "%");
            stmt.setString(2, "%" + criterio + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{rs.getInt("id"), rs.getString("nombre"), rs.getString("dni"), rs.getString("celular"), rs.getString("rol")});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al buscar usuarios: " + e.getMessage());
        }
    }

    private void eliminarUsuario(int id) {
        try {
            PreparedStatement stmt = conexion.prepareStatement("DELETE FROM Usuarios WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar usuario: " + e.getMessage());
        }
    }

    private void mostrarFormularioRegistro() {
        JTextField nombre = new JTextField();
        JTextField dni = new JTextField();
        JTextField celular = new JTextField();
        JTextField rol = new JTextField();
        JPasswordField pass = new JPasswordField();

        Object[] campos = {
            "Nombre:", nombre,
            "DNI:", dni,
            "Celular:", celular,
            "Rol:", rol,
            "Contraseña:", pass
        };

        int opcion = JOptionPane.showConfirmDialog(this, campos, "Registrar nuevo usuario", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            try {
                String sql = "INSERT INTO Usuarios (nombre, dni, celular, rol, password) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conexion.prepareStatement(sql);
                stmt.setString(1, nombre.getText());
                stmt.setString(2, dni.getText());
                stmt.setString(3, celular.getText());
                stmt.setString(4, rol.getText());
                stmt.setString(5, new String(pass.getPassword()));
                stmt.executeUpdate();
                cargarUsuarios();
                JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al registrar usuario: " + e.getMessage());
            }
        }
    }
} 
