package com.sistemadmin.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class RepositorioPanel extends JPanel {
    private DefaultListModel<String> recetasModel;
    private JList<String> listaRecetas;
    private Connection conexion;

    public RepositorioPanel() {
        setLayout(null);
        setBackground(Color.WHITE);

        conectarBD();

        JLabel titulo = new JLabel("Repositorio de recetas");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBounds(300, 20, 400, 30);
        add(titulo);

        JTextField txtBuscar = new JTextField("Buscar");
        txtBuscar.setBounds(200, 70, 400, 30);
        add(txtBuscar);

        JButton btnBuscar = new JButton("🔍");
        btnBuscar.setBounds(610, 70, 50, 30);
        btnBuscar.setBackground(new Color(39, 127, 179));
        btnBuscar.setForeground(Color.WHITE);
        add(btnBuscar);

        JPanel contenedor = new JPanel();
        contenedor.setLayout(null);
        contenedor.setBackground(new Color(245, 248, 250));
        contenedor.setBounds(100, 120, 600, 400);
        add(contenedor);

        JLabel lblCategorias = new JLabel("Categorías:");
        lblCategorias.setFont(new Font("Arial", Font.BOLD, 14));
        lblCategorias.setBounds(20, 20, 150, 20);
        contenedor.add(lblCategorias);

        JLabel lblEmojiCategorias = new JLabel("🥗 Ensaladas | 🍝 Pastas | 🍰 Postres | 🍲 Sopas");
        lblEmojiCategorias.setBounds(20, 40, 400, 20);
        contenedor.add(lblEmojiCategorias);

        JLabel lblDestacadas = new JLabel("Recetas Destacadas:");
        lblDestacadas.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDestacadas.setBounds(20, 70, 200, 20);
        contenedor.add(lblDestacadas);

        JTextArea recetasDestacadas = new JTextArea("- Tarta de Manzana\n- Ensalada palta\n- Sopa de verduras");
        recetasDestacadas.setBounds(40, 90, 200, 60);
        recetasDestacadas.setEditable(false);
        recetasDestacadas.setBackground(new Color(245, 248, 250));
        contenedor.add(recetasDestacadas);

        JLabel lblRecetas = new JLabel("Recetas:");
        lblRecetas.setFont(new Font("Arial", Font.PLAIN, 14));
        lblRecetas.setBounds(20, 160, 100, 20);
        contenedor.add(lblRecetas);

        recetasModel = new DefaultListModel<>();
        cargarRecetas();

        listaRecetas = new JList<>(recetasModel);
        listaRecetas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(listaRecetas);
        scroll.setBounds(40, 180, 400, 200);
        contenedor.add(scroll);

        JButton btnAbrir = new JButton("Abrir");
        btnAbrir.setBounds(460, 180, 100, 30);
        contenedor.add(btnAbrir);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(450, 540, 100, 30);
        add(btnAgregar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(560, 540, 100, 30);
        add(btnEliminar);

        btnAbrir.addActionListener((ActionEvent e) -> {
            int selectedIndex = listaRecetas.getSelectedIndex();
            if (selectedIndex != -1) {
                String nombre = recetasModel.getElementAt(selectedIndex);
                mostrarDetalleReceta(nombre);
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una receta para ver detalles.");
            }
        });

        btnAgregar.addActionListener((ActionEvent e) -> {
            JTextField nombreField = new JTextField(25);
            JTextArea ingredientesArea = new JTextArea(5, 20);
            JTextArea descripcionArea = new JTextArea(5, 20);

            JScrollPane scrollIngredientes = new JScrollPane(ingredientesArea);
            JScrollPane scrollDescripcion = new JScrollPane(descripcionArea);

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Nombre de la receta:"));
            panel.add(nombreField);
            panel.add(new JLabel("Ingredientes:"));
            panel.add(scrollIngredientes);
            panel.add(new JLabel("Descripción:"));
            panel.add(scrollDescripcion);

            int result = JOptionPane.showConfirmDialog(this, panel, "Agregar nueva receta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String nombre = nombreField.getText().trim();
                String ingredientes = ingredientesArea.getText().trim();
                String descripcion = descripcionArea.getText().trim();
                if (!nombre.isEmpty() && !descripcion.isEmpty() && !ingredientes.isEmpty()) {
                    guardarReceta(nombre, descripcion, ingredientes);
                    recetasModel.addElement(nombre);
                }
            }
        });

        btnEliminar.addActionListener((ActionEvent e) -> {
            int selectedIndex = listaRecetas.getSelectedIndex();
            if (selectedIndex != -1) {
                String receta = recetasModel.getElementAt(selectedIndex);
                eliminarReceta(receta);
                recetasModel.remove(selectedIndex);
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una receta para eliminar.");
            }
        });

        btnBuscar.addActionListener((ActionEvent e) -> {
            String termino = txtBuscar.getText().trim();
            if (!termino.isEmpty()) {
                buscarRecetas(termino);
            } else {
                recetasModel.clear();
                cargarRecetas();
            }
        });
    }

    private void conectarBD() {
        try {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=SistemaOlla;encrypt=true;trustServerCertificate=true";
            String user = "sa";
            String password = "tumamonada1526";
            conexion = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error de conexión a la base de datos: " + e.getMessage());
        }
    }

    private void cargarRecetas() {
        if (conexion != null) {
            try {
                Statement stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT nombre FROM Recetas");
                while (rs.next()) {
                    recetasModel.addElement(rs.getString("nombre"));
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al cargar recetas: " + e.getMessage());
            }
        }
    }

    private void buscarRecetas(String termino) {
        recetasModel.clear();
        if (conexion != null) {
            try {
                PreparedStatement stmt = conexion.prepareStatement("SELECT nombre FROM Recetas WHERE nombre LIKE ?");
                stmt.setString(1, "%" + termino + "%");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    recetasModel.addElement(rs.getString("nombre"));
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al buscar recetas: " + e.getMessage());
            }
        }
    }

    private void mostrarDetalleReceta(String nombre) {
        try {
            PreparedStatement stmt = conexion.prepareStatement("SELECT descripcion, ingredientes FROM Recetas WHERE nombre = ?");
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String descripcion = rs.getString("descripcion");
                String ingredientes = rs.getString("ingredientes");

                JTextArea detalle = new JTextArea("Receta: " + nombre + "\n\nIngredientes:\n" + ingredientes + "\n\nDescripción:\n" + descripcion);
                detalle.setLineWrap(true);
                detalle.setWrapStyleWord(true);
                detalle.setEditable(false);

                JScrollPane scrollPane = new JScrollPane(detalle);
                scrollPane.setPreferredSize(new Dimension(400, 300));

                JOptionPane.showMessageDialog(this, scrollPane, "Detalle de Receta", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener detalle: " + e.getMessage());
        }
    }

    private void guardarReceta(String nombre, String descripcion, String ingredientes) {
        try {
            PreparedStatement stmt = conexion.prepareStatement("INSERT INTO Recetas (nombre, descripcion, ingredientes) VALUES (?, ?, ?)");
            stmt.setString(1, nombre);
            stmt.setString(2, descripcion);
            stmt.setString(3, ingredientes);
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar receta: " + e.getMessage());
        }
    }

    private void eliminarReceta(String nombre) {
        try {
            PreparedStatement stmt = conexion.prepareStatement("DELETE FROM Recetas WHERE nombre = ?");
            stmt.setString(1, nombre);
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar receta: " + e.getMessage());
        }
    }
}
