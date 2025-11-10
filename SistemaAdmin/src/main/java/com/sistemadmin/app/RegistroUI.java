package com.sistemadmin.app;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegistroUI extends JFrame {
    public RegistroUI() {
        setTitle("Registro - Hijos de Santa Fe");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        // --- Panel Izquierdo ---
        JPanel panelIzq = new JPanel();
        panelIzq.setBackground(new Color(39, 127, 179));
        panelIzq.setLayout(null);

        JLabel titulo = new JLabel("<html><center>Registro para la<br>Ollita Comunitaria</center></html>");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setBounds(50, 30, 250, 60);
        panelIzq.add(titulo);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(50, 100, 100, 20);
        panelIzq.add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(50, 120, 200, 25);
        panelIzq.add(txtNombre);

        JLabel lblDni = new JLabel("DNI:");
        lblDni.setForeground(Color.WHITE);
        lblDni.setBounds(50, 160, 100, 20);
        panelIzq.add(lblDni);

        JTextField txtDni = new JTextField();
        txtDni.setBounds(50, 180, 200, 25);
        panelIzq.add(txtDni);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setForeground(Color.WHITE);
        lblPass.setBounds(50, 220, 100, 20);
        panelIzq.add(lblPass);

        JPasswordField txtPass = new JPasswordField();
        txtPass.setBounds(50, 240, 200, 25);
        panelIzq.add(txtPass);

        JLabel lblCelular = new JLabel("Celular:");
        lblCelular.setForeground(Color.WHITE);
        lblCelular.setBounds(50, 280, 100, 20);
        panelIzq.add(lblCelular);

        JTextField txtCelular = new JTextField();
        txtCelular.setBounds(50, 300, 200, 25);
        panelIzq.add(txtCelular);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(80, 350, 140, 30);
        btnRegistrar.setBackground(new Color(100, 180, 255));
        btnRegistrar.setFocusPainted(false);
        panelIzq.add(btnRegistrar);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(80, 390, 140, 30);
        btnVolver.setBackground(new Color(180, 100, 100));
        btnVolver.setFocusPainted(false);
        panelIzq.add(btnVolver);

        // Acción al presionar "Registrar"
        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String dni = txtDni.getText().trim();
            String pass = new String(txtPass.getPassword()).trim();
            String celular = txtCelular.getText().trim();

            if (nombre.isEmpty() || dni.isEmpty() || pass.isEmpty() || celular.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.");
                return;
            }

            String url = "jdbc:sqlserver://localhost:1433;databaseName=SistemaOlla;encrypt=true;trustServerCertificate=true";
            String user = "sa";
            String passDb = "tumamonada1526";

            try (Connection conn = DriverManager.getConnection(url, user, passDb)) {
                String query = "INSERT INTO UsuariosAdmin (nombre, password, dni, celular) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, nombre);
                stmt.setString(2, pass);
                stmt.setString(3, dni);
                stmt.setString(4, celular);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "✅ ¡Registro exitoso!");
                dispose();
                new LoginUI();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "❌ Error al registrar: " + ex.getMessage());
            }
        });

        // Acción al presionar "Volver"
        btnVolver.addActionListener(e -> {
            dispose();
            new LoginUI();
        });

        // --- Panel Derecho ---
        JPanel panelDer = new JPanel();
        panelDer.setBackground(Color.WHITE);
        panelDer.setLayout(null);

        JLabel tituloApp = new JLabel("<html><center><h1 style='color:#188fa7;'>HIJOS DE<br>SANTA FE</h1></center></html>");
        tituloApp.setFont(new Font("Arial", Font.BOLD, 24));
        tituloApp.setBounds(100, 30, 300, 80);
        panelDer.add(tituloApp);

        ImageIcon img = new ImageIcon("D:\\FotosJava\\ollaComunitariaAña.jpg");
        JLabel imgLabel = new JLabel(new ImageIcon(img.getImage().getScaledInstance(250, 150, Image.SCALE_SMOOTH)));
        imgLabel.setBounds(100, 120, 250, 150);
        panelDer.add(imgLabel);

        JLabel frase = new JLabel("\"Un nuevo comienzo, un nuevo plato.\"");
        frase.setBounds(100, 280, 300, 30);
        panelDer.add(frase);

        JLabel mensaje = new JLabel("¡Regístrate para participar!");
        mensaje.setBounds(130, 320, 250, 30);
        mensaje.setForeground(Color.GRAY);
        panelDer.add(mensaje);

        // Agregar paneles
        add(panelIzq);
        add(panelDer);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
