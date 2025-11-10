package com.sistemadmin.app;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class PanelPrincipal extends JFrame {
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private HashMap<String, JPanel> vistas = new HashMap<>();

    public PanelPrincipal(String nombreUsuario) {
        setTitle("Sistema Integral de Gestión");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel lateral (sidebar)
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(39, 127, 179));
        sidebar.setPreferredSize(new Dimension(200, getHeight()));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        // Título del sistema
        JLabel tituloSistema = new JLabel("<html><center>Sistema Integral de<br>Gestión de la ollita<br>Comunitaria</center></html>", SwingConstants.CENTER);
        tituloSistema.setForeground(Color.WHITE);
        tituloSistema.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(tituloSistema);
        sidebar.add(Box.createVerticalStrut(30));

        // Panel contenedor con CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // Panel de inicio con diseño visual
        JPanel panelInicio = new JPanel(null);
        panelInicio.setBackground(Color.WHITE);

        JLabel lblBienvenidos = new JLabel("BIENVENIDOS");
        lblBienvenidos.setFont(new Font("Arial", Font.BOLD, 28));
        lblBienvenidos.setBounds(380, 10, 400, 40);
        panelInicio.add(lblBienvenidos);

        // Menú semanal
        String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
        String[] platos = {"Lomo Saltado", "Ají de Gallina", "Arroz con Pollo", "Seco de Cordero", "Papa Rellena"};
        String[] descripciones = {
                "Un clásico de la cocina peruana con trozos de carne, cebolla, tomate y papas fritas.",
                "Crema espesa de gallina deshilachada en salsa de ají amarillo, con arroz, huevo y aceituna.",
                "Arroz con pollo, zanahoria y alverjas, con salsa de culantro.",
                "Guiso de carne con frejoles y arroz.",
                "Masa de papa rellena de carne, huevo y aceitunas, frita hasta dorar."
        };

        for (int i = 0; i < dias.length; i++) {
            JLabel lblDia = new JLabel("<html><b>" + dias[i] + ":</b> " + platos[i] + "<br><small>" + descripciones[i] + "</small></html>");
            lblDia.setBounds(40 + (i * 180), 70, 170, 80);
            panelInicio.add(lblDia);
        }

        // Marco de precios agrandado visualmente con borde y más alto
        JPanel marcoPrecios = new JPanel();
        marcoPrecios.setLayout(new BorderLayout());
        marcoPrecios.setBounds(80, 250, 300, 250); // altura aumentada de 200 a 250
        marcoPrecios.setBorder(BorderFactory.createTitledBorder("Precios"));

        JLabel lblPrecios = new JLabel("<html><table border='1' cellpadding='10' cellspacing='0' style='font-size:14px;'>"
                + "<tr><th>Porción</th><th>Precio S/</th></tr>"
                + "<tr><td>Segundo solo</td><td>S/5.00</td></tr>"
                + "<tr><td>Sopa</td><td>S/2.00</td></tr>"
                + "<tr><td>Bebida</td><td>S/1.00</td></tr>"
                + "<tr><td>Postre</td><td>S/1.00</td></tr>"
                + "</table></html>", SwingConstants.CENTER);
        marcoPrecios.add(lblPrecios, BorderLayout.CENTER);
        panelInicio.add(marcoPrecios);

        // Imagen de calendario desplazada
        ImageIcon imgIcon = new ImageIcon("D:/FotosJava/calendario2025.jpg"); // Asegúrate de que exista
        JLabel lblCalendario = new JLabel(new ImageIcon(imgIcon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH)));
        lblCalendario.setBounds(420, 250, 200, 150);
        panelInicio.add(lblCalendario);

        // Añadir vistas al HashMap y al contentPanel
        vistas.put("Inicio", panelInicio);
        vistas.put("Inventario", new InventarioPanel());
        vistas.put("Proveedores", new ProveedoresPanel());
        vistas.put("Repositorio", new RepositorioPanel());
        vistas.put("Eventos", new EventosPanel());
        vistas.put("Roles", new RolesPanel());
        vistas.put("Información de los usuarios", new InformacionUsuariosPanel());

        for (String clave : vistas.keySet()) {
            contentPanel.add(vistas.get(clave), clave);
        }

        // Botones del menú lateral
        String[] opciones = {"Inicio", "Información de los usuarios", "Inventario", "Eventos", "Roles", "Repositorio", "Proveedores"};
        for (String texto : opciones) {
            JButton btn = new JButton(texto);
            btn.setMaximumSize(new Dimension(180, 40));
            btn.setFocusPainted(false);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            sidebar.add(Box.createVerticalStrut(10));
            sidebar.add(btn);

            // Acción para cambiar de panel
            btn.addActionListener(e -> {
                if (vistas.containsKey(texto)) {
                    cardLayout.show(contentPanel, texto);
                } else {
                    JOptionPane.showMessageDialog(this, "Sección aún en desarrollo: " + texto);
                }
            });
        }

        // Botón cerrar sesión
        JButton btnCerrar = new JButton("Cerrar Sección");
        btnCerrar.setMaximumSize(new Dimension(180, 40));
        btnCerrar.setBackground(new Color(200, 60, 60));
        btnCerrar.setFocusPainted(false);
        btnCerrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCerrar.addActionListener(e -> {
            dispose();
            new LoginUI();
        });
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(btnCerrar);
        sidebar.add(Box.createVerticalStrut(20));

        // Añadir todo al frame
        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);

        // Mostrar Inicio por defecto
        cardLayout.show(contentPanel, "Inicio");
    }
}