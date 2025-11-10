package com.sistemadmin.app;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginUI extends JFrame {
    public LoginUI() {
        setTitle("Sistema de Gestión - Hijos de Santa Fe");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2)); // Dividir en dos columnas

        // Panel azul (izquierdo)
        JPanel panelIzq = new JPanel();
        panelIzq.setBackground(new Color(39, 127, 179));
        panelIzq.setLayout(null);

        // Panel blanco (derecho)
        JPanel panelDer = new JPanel();
        panelDer.setBackground(Color.WHITE);
        panelDer.setLayout(null);

        // --- Panel Izquierdo: Formulario de login ---
        JLabel titulo = new JLabel("<html><center>Sistema Integral de<br>Gestión de la ollita<br>Comunitaria</center></html>");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setBounds(50, 40, 200, 80);
        panelIzq.add(titulo);

        JLabel dniLabel = new JLabel("Número de DNI");
        dniLabel.setForeground(Color.WHITE);
        dniLabel.setBounds(50, 140, 100, 20);
        panelIzq.add(dniLabel);

        JTextField dniField = new JTextField();
        dniField.setBounds(50, 160, 200, 25);
        panelIzq.add(dniField);

        JLabel passLabel = new JLabel("Contraseña");
        passLabel.setForeground(Color.WHITE);
        passLabel.setBounds(50, 200, 100, 20);
        panelIzq.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(50, 220, 200, 25);
        panelIzq.add(passField);

        JButton btnLogin = new JButton("Iniciar sesión");
        btnLogin.setBounds(80, 260, 140, 30);
        btnLogin.setBackground(new Color(100, 180, 255));
        btnLogin.setFocusPainted(false);
        panelIzq.add(btnLogin);

        // Acción al hacer clic en "Iniciar sesión"
        btnLogin.addActionListener(e -> {
            String dni = dniField.getText().trim();
            String password = new String(passField.getPassword()).trim();

            // Conexión a SQL Server
            String url = "jdbc:sqlserver://localhost:1433;databaseName=SistemaOlla;encrypt=true;trustServerCertificate=true";
            String user = "sa";       // Cambia si usas otro usuario
            String pass = "tumamonada1526";     // Cambia a tu contraseña real

            try (Connection conn = DriverManager.getConnection(url, user, pass)) {
                String sql = "SELECT * FROM UsuariosAdmin WHERE dni = ? AND password = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, dni);
                stmt.setString(2, password);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "✅ ¡Bienvenido administrador!");
                    dispose();
                    new PanelPrincipal(dni); // Abre nueva ventana principal
                } else {
                    JOptionPane.showMessageDialog(this, "❌ DNI o contraseña incorrectos.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "❌ Error al conectar: " + ex.getMessage());
            }
        });

        JLabel noCuenta = new JLabel("¿Aún no estás registrado?");
        noCuenta.setForeground(Color.WHITE);
        noCuenta.setBounds(60, 310, 180, 20);
        panelIzq.add(noCuenta);

        JButton btnRegistro = new JButton("¡Regístrate!");
        btnRegistro.setBounds(80, 340, 140, 30);
        btnRegistro.setBackground(new Color(100, 180, 255));
        btnRegistro.setFocusPainted(false);
        panelIzq.add(btnRegistro);

        // Acción al hacer clic en "¡Regístrate!"
        btnRegistro.addActionListener(e -> {
            dispose();            // Cierra la ventana actual
            new RegistroUI();    // Abre la ventana de registro
        });

        // --- Panel Derecho: Mensaje e imagen ---
        JLabel tituloApp = new JLabel("<html><center><h1 style='color:#188fa7;'>HIJOS DE<br>SANTA FE</h1></center></html>");
        tituloApp.setFont(new Font("Arial", Font.BOLD, 24));
        tituloApp.setBounds(100, 30, 300, 80);
        panelDer.add(tituloApp);

        // Imagen
        ImageIcon img = new ImageIcon("D:\\FotosJava\\ollaComunitariaAña.jpg"); // Asegúrate de que exista
        JLabel imgLabel = new JLabel(new ImageIcon(img.getImage().getScaledInstance(250, 150, Image.SCALE_SMOOTH)));
        imgLabel.setBounds(100, 120, 250, 150);
        panelDer.add(imgLabel);

        JLabel frase = new JLabel("\"En cada plato, compartimos esperanza.\"");
        frase.setBounds(100, 280, 300, 30);
        panelDer.add(frase);

        JLabel mensaje = new JLabel("¡Inicia sesión para empezar!");
        mensaje.setBounds(130, 320, 250, 30);
        mensaje.setForeground(Color.GRAY);
        panelDer.add(mensaje);

        // Agregar paneles
        add(panelIzq);
        add(panelDer);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginUI();
    }
}
