package com.taller.ui;

import com.taller.dao.BitacoraDAO;
import com.taller.dao.UsuarioDAO;
import com.taller.modelo.Usuario;
import com.taller.util.Sesion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class LoginFrame extends JFrame {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final BitacoraDAO bitacoraDAO = new BitacoraDAO();

    private JTextField txtUsuario;
    private JPasswordField txtClave;

    public LoginFrame() {
        setTitle("Hotwheels Tam - Iniciar sesion");
        setSize(480, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        construirUI();
    }

    private void construirUI() {
        // Panel principal con gradiente
        JPanel panelPrincipal = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, Estilos.AZUL_OSCURO, 0, getHeight(), new Color(10, 20, 45));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // ---- ENCABEZADO con logo centrado ----
        JPanel encabezado = new JPanel();
        encabezado.setOpaque(false);
        encabezado.setLayout(new BoxLayout(encabezado, BoxLayout.Y_AXIS));
        encabezado.setBorder(new EmptyBorder(25, 20, 15, 20));

        ImageIcon logoIcon = obtenerLogoEstilizado("imagenes/logos/hotwheels_tam.jpg", 220, 78);
        JLabel iconoTitulo;
        if (logoIcon != null) {
            iconoTitulo = new JLabel(logoIcon);
        } else {
            iconoTitulo = new JLabel("HT");
            iconoTitulo.setFont(new Font("SansSerif", Font.BOLD, 48));
            iconoTitulo.setForeground(Estilos.NARANJA);
        }
        iconoTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titulo = new JLabel("Hotwheels Tam");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 30));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Sistema Profesional de Gestión");
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitulo.setForeground(Estilos.NARANJA);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        encabezado.add(iconoTitulo);
        encabezado.add(Box.createVerticalStrut(12));
        encabezado.add(titulo);
        encabezado.add(Box.createVerticalStrut(4));
        encabezado.add(subtitulo);

        // ---- FORMULARIO ----
        JPanel tarjeta = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 20));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
            }
        };
        tarjeta.setOpaque(false);
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(new EmptyBorder(28, 40, 28, 40));

        JLabel lblAcceso = new JLabel("Iniciar Sesión");
        lblAcceso.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblAcceso.setForeground(Color.WHITE);
        lblAcceso.setAlignmentX(Component.CENTER_ALIGNMENT);
        tarjeta.add(lblAcceso);
        tarjeta.add(Box.createVerticalStrut(22));

        JLabel lblEmail = new JLabel("Usuario");
        lblEmail.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblEmail.setForeground(new Color(180, 200, 240));
        lblEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
        tarjeta.add(lblEmail);
        tarjeta.add(Box.createVerticalStrut(6));

        txtUsuario = new JTextField();
        estilosCampo(txtUsuario);
        tarjeta.add(txtUsuario);
        tarjeta.add(Box.createVerticalStrut(16));

        JLabel lblPass = new JLabel("Contraseña");
        lblPass.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblPass.setForeground(new Color(180, 200, 240));
        lblPass.setAlignmentX(Component.CENTER_ALIGNMENT);
        tarjeta.add(lblPass);
        tarjeta.add(Box.createVerticalStrut(6));

        txtClave = new JPasswordField();
        estilosCampo(txtClave);
        tarjeta.add(txtClave);
        tarjeta.add(Box.createVerticalStrut(28));

        JButton btnLogin = crearBotonPremium("ENTRAR AL SISTEMA");
        btnLogin.addActionListener(e -> intentarLogin());
        tarjeta.add(btnLogin);
        tarjeta.add(Box.createVerticalStrut(16));



        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setOpaque(false);
        contenedor.setBorder(new EmptyBorder(0, 30, 30, 30));
        contenedor.add(tarjeta, BorderLayout.CENTER);

        panelPrincipal.add(encabezado, BorderLayout.NORTH);
        panelPrincipal.add(contenedor, BorderLayout.CENTER);
        setContentPane(panelPrincipal);
        getRootPane().setDefaultButton(btnLogin);
    }

    private void estilosCampo(JTextField campo) {
        campo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campo.setMaximumSize(new Dimension(300, 40));
        campo.setAlignmentX(Component.CENTER_ALIGNMENT);
        campo.setBackground(new Color(255, 255, 255, 30));
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(Color.WHITE);
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 140, 220, 150), 1, true),
            new EmptyBorder(6, 12, 6, 12)
        ));
        campo.setOpaque(false);
    }

    private JButton crearBotonPremium(String texto) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(Estilos.NARANJA.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(Estilos.NARANJA.brighter());
                } else {
                    GradientPaint gp = new GradientPaint(0, 0, Estilos.NARANJA, 0, getHeight(), Estilos.NARANJA.darker());
                    g2.setPaint(gp);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 15));
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(texto)) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(texto, x, y);
                g2.dispose();
            }
        };
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(300, 46));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void intentarLogin() {
        try {
            String username = com.taller.util.SeguridadUtil.normalizeEmail(txtUsuario.getText().trim());
            String clave = new String(txtClave.getPassword());

            if (username.isEmpty() || clave.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingresa usuario y contraseña", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!com.taller.util.SeguridadUtil.esEmailValido(username)) {
                JOptionPane.showMessageDialog(this, "El nombre de usuario debe ser un correo electrónico válido", "Correo inválido", JOptionPane.ERROR_MESSAGE);
                bitacoraDAO.registrar("LOGIN_RECHAZADO", "Formato de correo inválido: " + username);
                return;
            }

            Usuario u = usuarioDAO.autenticar(username, clave);
            if (u == null) {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
                bitacoraDAO.registrar("LOGIN_FALLIDO", "Intento fallido: " + username);
                return;
            }

            Sesion.iniciar(u);
            bitacoraDAO.registrar("LOGIN", "Inicio de sesión: " + username + " (" + u.getRol() + ")");

            // El cliente tiene su propia vista de solo lectura
            if (u.getRol() == com.taller.modelo.RolUsuario.CLIENTE) {
                new PanelClienteVista().setVisible(true);
            } else {
                new MainDashboard().setVisible(true);
            }
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error del sistema al intentar iniciar sesión:\n" + e.getMessage()
                + "\n\nPor favor, verifica que el servicio de MySQL (XAMPP) esté corriendo.",
                "Error de Conexión / Sistema", JOptionPane.ERROR_MESSAGE);
        }
    }

    private ImageIcon obtenerLogoEstilizado(String path, int targetW, int targetH) {
        if (!new java.io.File(path).exists()) return null;
        try {
            ImageIcon icon = new ImageIcon(path);
            Image img = icon.getImage();
            int w = img.getWidth(null);
            int h = img.getHeight(null);
            if (w <= 0 || h <= 0) return null;

            BufferedImage bimg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = bimg.createGraphics();
            g2.drawImage(img, 0, 0, null);
            g2.dispose();

            // Reemplazar fondo blanco con transparente
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    int rgb = bimg.getRGB(x, y);
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >> 8) & 0xFF;
                    int b = rgb & 0xFF;
                    if (r > 230 && g > 230 && b > 230) {
                        bimg.setRGB(x, y, 0x00FFFFFF); // transparente
                    }
                }
            }
            Image scaled = bimg.getScaledInstance(targetW, targetH, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (Exception e) {
            return null;
        }
    }
}
